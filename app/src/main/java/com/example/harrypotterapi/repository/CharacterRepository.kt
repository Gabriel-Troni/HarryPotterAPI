package com.example.harrypotterapi.repository

import com.example.harrypotterapi.model.Character
import com.example.harrypotterapi.network.HarryPotterAPI
import retrofit2.Response

class CharacterRepository(private val api: HarryPotterAPI) {

    suspend fun getCharacterById(id: String): Response<Character> {
        return api.getCharacterById(id)
    }

    suspend fun getAllCharacters(): Response<List<Character>> {
        return api.getAllCharacters()
    }
}
