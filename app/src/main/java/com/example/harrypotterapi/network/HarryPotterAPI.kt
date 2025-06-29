package com.example.harrypotterapi.network

import com.example.harrypotterapi.model.Spell
import com.example.harrypotterapi.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterAPI {
    @GET("api/spells")
    suspend fun getSpells(): Response<List<Spell>>

    @GET("api/characters/house/{house}")
    suspend fun getStudentsByHouse(@Path("house") house: String): Response<List<Character>>

}