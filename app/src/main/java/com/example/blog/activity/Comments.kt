package com.example.blog.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.blog.R
import com.example.blog.activity.data.DataRepository
import com.example.blog.adapter.CommentAdapter
import com.example.blog.dataclass.CommentApi
import com.example.blog.dataclass.Model
import kotlinx.android.synthetic.main.feed_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Comments:Fragment() {

    lateinit var commentsList: List<Model.Comment>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.feed_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCommentsFromApi(requireArguments().getInt("postId",0))

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

    private fun getCommentsFromApi(postId: Int) {
        val dataRepository = DataRepository()
        val call : Call<List<Model.Comment>> = dataRepository.getRetrofit().create(CommentApi::class.java).GetComments(postId)

        call.enqueue(object : Callback<List<Model.Comment>> {
            override fun onResponse(
                call: Call<List<Model.Comment>>,
                response: Response<List<Model.Comment>>
            ) {
                loading(false)
                commentsList = response.body()!!
                val adapterTest = CommentAdapter(commentsList)
                feed_list.adapter = adapterTest
            }

            override fun onFailure(call: Call<List<Model.Comment>>, t: Throwable) {
                Log.w("Server Error", t.message.toString())
                println("Failed connection")
            }

        })
    }
}