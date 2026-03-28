package com.example.testkmp.data.repositories

import com.example.testkmp.data.network.ApiService
import com.example.testkmp.domain.repositories.ApiRepository

class ApiRepositoryImpl(
    private val apiService: ApiService
) : ApiRepository {

    override suspend fun getMessage(): Result<String> {
        return apiService.sendMessage()
            .map { response ->
                response.choices.firstOrNull()?.message?.content ?: ""
            }
    }
}