package com.example.blog.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.adapter.AlbumAdapter
import com.example.blog.dataclass.AlbumApi
import com.example.blog.dataclass.Model
import kotlinx.android.synthetic.main.album_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Albums:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.album_layout)

        recycler_view.layoutManager =
            LinearLayoutManager(this@Albums, RecyclerView.VERTICAL, true)

        getAlbumsFromApi()

        back_btn3.setOnClickListener {
            finish()
        }
    }

    private fun getAlbumsFromApi() {

        val dataRepository = DataRepository()
        val call : Call<List<Model.Album>> = dataRepository.getRetrofit()
            .create(AlbumApi::class.java).GetAlbums(2)

        call.enqueue(object : Callback<List<Model.Album>> {
            override fun onResponse(
                call: Call<List<Model.Album>>,
                response: Response<List<Model.Album>>
            ) {
                Log.w(
                    "Server Ok",
                    response.body()?.get(0)!!.title + "   |||   " + response.body()?.get(0)!!.userId
                )
                println("this is response :  \n"
                        +"Title :  "+ response.body()!![0].title + "  ||  UserId" + response.body()!![0].userId)


                recycler_view.adapter = AlbumAdapter(response.body()!!, this@Albums)

            }

            override fun onFailure(call: Call<List<Model.Album>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }
        })
    }
}