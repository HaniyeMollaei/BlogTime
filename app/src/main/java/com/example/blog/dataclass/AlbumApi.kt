package com.example.blog.dataclass

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApi {
    @GET("albums")
    fun GetAlbums(@Query("userId") userId : Int ) : Call<List<Model.Album>>
}