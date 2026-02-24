package com.example.testkmp.domain.repositories

interface DatabaseRepository {

    suspend fun getList() : List<String>

}