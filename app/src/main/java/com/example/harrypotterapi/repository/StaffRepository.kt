package com.example.harrypotterapi.repository

import com.example.harrypotterapi.model.Staff
import com.example.harrypotterapi.network.HarryPotterAPI
import retrofit2.Response

class StaffRepository(private val api: HarryPotterAPI) {
    suspend fun getHogwartsStaff(): Response<List<Staff>> {
        return api.getHogwartsStaff()
    }
}
