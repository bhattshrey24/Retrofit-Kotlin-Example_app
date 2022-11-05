package com.example.retrofitkotlinexampleapplication

import com.google.gson.annotations.SerializedName

data class Post(
    var userId: Int = 0,
    val id: Int = 0,
    val title: String? = null,
    @SerializedName("body")
    var text: String? = null
)