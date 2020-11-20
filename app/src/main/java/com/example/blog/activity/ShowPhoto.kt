package com.example.blog.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.adapter.GridViewAdapter
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.PhotoApi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_photo_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowPhoto:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_photo_layout)
        getPhotoFromApi(intent.getIntExtra("photoId" , 0))

    }

    private fun getPhotoFromApi(photoId: Int) {
        val dataRepository = DataRepository()
        val call : Call<List<Model.Photo>> = dataRepository.getRetrofit()
            .create(PhotoApi::class.java).GetOnePhoto(photoId)

        call.enqueue(object : Callback<List<Model.Photo>> {
            override fun onResponse(
                call: Call<List<Model.Photo>>,
                response: Response<List<Model.Photo>>
            ) {
                Log.w(
                    "Server Ok",
                    response.body()?.get(0)!!.title + "   |||   " + response.body()?.get(0)!!.id
                )
                println("this is response :  \n"
                        +"Title :  "+ response.body()!![0].title + "  ||  Id" + response.body()!![0].id)

                Picasso.get().load(response.body()!![0].url).into(photo)
            }

            override fun onFailure(call: Call<List<Model.Photo>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }
        })
    }
}