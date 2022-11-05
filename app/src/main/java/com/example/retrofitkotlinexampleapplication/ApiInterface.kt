package com.example.retrofitkotlinexampleapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("posts")
    fun getPost(): Call<List<Post?>?>

    @GET("posts")
    fun getPostUsingCoroutine(): List<Post?>?
}