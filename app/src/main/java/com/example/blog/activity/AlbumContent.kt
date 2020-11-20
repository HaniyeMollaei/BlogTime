package com.example.blog.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.adapter.GridViewAdapter
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.PhotoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumContent : AppCompatActivity(){

    lateinit var gridView: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.album_content_page)
        val albumId = intent.getIntExtra("albumId" , 0)
        Toast.makeText(this , "salam $albumId" , Toast.LENGTH_SHORT).show()
        gridView = findViewById(R.id.album_gridView)
        getPhotosFromApi(albumId)
    }

    private fun getPhotosFromApi(albumId: Int) {
        val dataRepository = DataRepository()
        val call : Call<List<Model.Photo>> = dataRepository.getRetrofit()
            .create(PhotoApi::class.java).GetPhotos(albumId)

        call.enqueue(object : Callback<List<Model.Photo>> {
            override fun onResponse(
                call: Call<List<Model.Photo>>,
                response: Response<List<Model.Photo>>
            ) {
                println("response : " +"id :  "+ response.body()!![0].id + "  **  title" + response.body()!![0].title)

                val mainAdapter = GridViewAdapter(this@AlbumContent, response.body()!!)
                gridView.adapter = mainAdapter
                gridView.onItemClickListener =
                    AdapterView.OnItemClickListener { _, _, position, _ ->
                        Toast.makeText(this@AlbumContent , "You CLicked " + position, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@AlbumContent , ShowPhoto::class.java)
                            .putExtra("photoId" , response.body()!![position].id))
                    }

            }

            override fun onFailure(call: Call<List<Model.Photo>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }
        })
    }
}
