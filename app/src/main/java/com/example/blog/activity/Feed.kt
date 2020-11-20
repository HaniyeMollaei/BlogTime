package com.example.blog.activity

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.adapter.PostAdapter
import com.example.blog.dataclass.Model
import com.example.blog.dataclass.PostApi
import kotlinx.android.synthetic.main.feed_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Feed:AppCompatActivity() {

    lateinit var postsList: List<Model.Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_layout)
        getPostsFromApi()

        back_btn2.setOnClickListener {
            finish()
        }
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

    private fun getPostsFromApi() {
        val dataRepository = DataRepository()
        val call : Call<List<Model.Post>> = dataRepository.getRetrofit().create(PostApi::class.java).GetPosts(2)

        call.enqueue(object : Callback<List<Model.Post>> {
            override fun onResponse(
                call: Call<List<Model.Post>>,
                response: Response<List<Model.Post>>
            ) {
                loading(false)
                postsList = response.body()!!
                printPosts(postsList)
                val adapterTest = PostAdapter(postsList)
                feed_list.adapter = adapterTest
            }

            override fun onFailure(call: Call<List<Model.Post>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }

        })
    }

    private fun printPosts(postsList: List<Model.Post>) {
        for (it in postsList.indices){
            println("user  " +it+"  Username : "+postsList[it].title+"    Email : "+postsList[it].body)

        }
    }
}