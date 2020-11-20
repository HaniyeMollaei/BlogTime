package com.example.blog.dataclass

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface PostApi {
    @GET("posts")
    fun GetPosts(@Query("userId") userId: Int) : Call<List<Model.Post>>

}