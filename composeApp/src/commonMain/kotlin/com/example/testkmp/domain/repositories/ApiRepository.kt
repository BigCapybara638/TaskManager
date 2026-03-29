package com.example.testkmp.domain.repositories

interface ApiRepository {

    suspend fun getMessage() : Result<String>

}