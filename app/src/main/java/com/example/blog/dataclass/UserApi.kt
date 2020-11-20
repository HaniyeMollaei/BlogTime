package com.example.blog.dataclass

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    fun GetUsers(@Query("username") username : String ) : Call<List<Model.User>>
}