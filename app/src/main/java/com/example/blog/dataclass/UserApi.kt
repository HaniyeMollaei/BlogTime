package com.example.blog.dataclass

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("https://jsonplaceholder.typicode.com/users")
    fun GetUsers() : Call<List<Model.User>>
}