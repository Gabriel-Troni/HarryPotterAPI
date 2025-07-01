package com.example.harrypotterapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://hp-api.onrender.com/" // ou o que sua API usar

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: HarryPotterAPI by lazy {
        retrofit.create(HarryPotterAPI::class.java)
    }
}
