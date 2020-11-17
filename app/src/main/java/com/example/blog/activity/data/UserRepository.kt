package com.example.blog.activity.data

import com.example.blog.dataclass.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserRepository {

    private val time = 30
    fun client():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(time.toLong(), TimeUnit.SECONDS)
            .writeTimeout(time.toLong(), TimeUnit.SECONDS)
            .readTimeout(time.toLong(), TimeUnit.SECONDS)
            .build()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .client(client())
            .build()
    }
}