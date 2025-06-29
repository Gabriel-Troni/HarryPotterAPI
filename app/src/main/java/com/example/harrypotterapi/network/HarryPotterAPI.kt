package com.example.harrypotterapi.network

import com.example.harrypotterapi.model.Spell
import retrofit2.Response
import retrofit2.http.GET

interface HarryPotterAPI {
    @GET("api/spells")
    suspend fun getSpells(): Response<List<Spell>>
}