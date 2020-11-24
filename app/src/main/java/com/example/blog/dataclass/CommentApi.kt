package com.example.blog.dataclass

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentApi {
    @GET("comments")
    fun GetComments(@Query("postId") postId: Int) : Call<List<Model.Comment>>

}