package com.example.blog.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.PhotoApi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.show_photo_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowPhoto:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_photo_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var imgId = requireArguments().getInt("photoId",0)
        getPhotoFromApi(imgId)

        next.setOnClickListener {
            getPhotoFromApi(++imgId)
        }
        prev.setOnClickListener {
            getPhotoFromApi(--imgId)
        }
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

                Picasso.get().load(response.body()!![0].url).into(photo)
            }

            override fun onFailure(call: Call<List<Model.Photo>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }
        })
    }
}