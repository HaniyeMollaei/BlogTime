package com.example.blog.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class Albums: Fragment(){

    private lateinit var userData : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, true)
        userData = activity?.getSharedPreferences("appUser" , Context.MODE_PRIVATE) ?: userData
        val data_tmp = userData.getString("appUser" , "-1*هانیه ملائی*Haniye_Mli*Haniyemolaei1378@gmail.com*تهران*09371544159*https://github.com/HaniyeMollaei*شرکت ...")
        getAlbumsFromApi(data_tmp!!.split("*")[0].toInt())

    }

    private fun getAlbumsFromApi(userId: Int) {

        val dataRepository = DataRepository()
        val call : Call<List<Model.Album>> = dataRepository.getRetrofit()
            .create(AlbumApi::class.java).GetAlbums(userId)

        call.enqueue(object : Callback<List<Model.Album>> {
            override fun onResponse(
                call: Call<List<Model.Album>>,
                response: Response<List<Model.Album>>
            ) {
                recycler_view.adapter = AlbumAdapter(response.body()!!, context)
            }

            override fun onFailure(call: Call<List<Model.Album>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }
        })
    }
}