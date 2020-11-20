package com.example.blog.dataclass

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {
    @GET("photos")
    fun GetPhotos(@Query("albumId") albumId: Int) : Call<List<Model.Photo>>

    @GET("photos")
    fun GetOnePhoto(@Query("id") id: Int) : Call<List<Model.Photo>>
}