package com.example.testkmp.data

import com.example.testkmp.domain.repositories.DatabaseRepository

class FakeRepositoryImpl : DatabaseRepository {

    val db = FakeDatabase()

    override suspend fun getList(): List<String> {
        return db.list
    }
}