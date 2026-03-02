package com.example.testkmp.data

import com.example.testkmp.domain.repositories.DatabaseRepository
import com.example.testkmp.domain.models.Task

class FakeRepositoryImpl : DatabaseRepository {

    val db = FakeDatabase()

    override fun getList(): List<Task> {
        return db.list
    }
}