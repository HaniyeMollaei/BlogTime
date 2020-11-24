package com.example.blog.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.adapter.PostAdapter
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.PostApi
import kotlinx.android.synthetic.main.feed_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Feed:Fragment() {

    lateinit var postsList: List<Model.Post>
    private lateinit var userData : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(R.layout.feed_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userData = activity?.getSharedPreferences("appUser" , Context.MODE_PRIVATE) ?: userData
        val data_tmp = userData.getString("appUser" , "-1*هانیه ملائی*Haniye_Mli*Haniyemolaei1378@gmail.com*تهران*09371544159*https://github.com/HaniyeMollaei*شرکت ...")
        getPostsFromApi( data_tmp!!.split("*")[0].toInt())
    }


    private fun loading(show: Boolean) {
        if(show){
            login_pb.visibility =  ProgressBar.VISIBLE
            feed_list.visibility = ListView.GONE
        } else {
            login_pb.visibility =  ProgressBar.GONE
            feed_list.visibility = ListView.VISIBLE

        }
    }

    private fun getPostsFromApi(userId: Int) {
        val dataRepository = DataRepository()
        val call : Call<List<Model.Post>> = dataRepository.getRetrofit().create(PostApi::class.java).GetPosts(userId)

        call.enqueue(object : Callback<List<Model.Post>> {
            override fun onResponse(
                call: Call<List<Model.Post>>,
                response: Response<List<Model.Post>>
            ) {
                loading(false)
                postsList = response.body()!!
                val adapterTest = PostAdapter(postsList , activity)
                feed_list.adapter = adapterTest
            }

            override fun onFailure(call: Call<List<Model.Post>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }

        })
    }
}