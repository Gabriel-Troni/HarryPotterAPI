package com.example.harrypotterapi.network

import com.example.harrypotterapi.model.Character
import com.example.harrypotterapi.model.Spell
import com.example.harrypotterapi.model.Staff
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterAPI {
    @GET("api/spells")
    suspend fun getSpells(): Response<List<Spell>>

    @GET("api/characters")
    suspend fun getAllCharacters(): Response<List<Character>>

    @GET("api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Response<Character>

    @GET("api/characters/staff")
    suspend fun getHogwartsStaff(): Response<List<Staff>>

    @GET("api/characters/house/{house}")
    suspend fun getStudentsByHouse(@Path("house") house: String): Response<List<Character>>
}