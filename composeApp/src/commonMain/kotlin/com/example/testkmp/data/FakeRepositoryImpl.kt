package com.example.testkmp.data

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.repositories.DatabaseRepository
import com.example.testkmp.domain.models.Task

class FakeRepositoryImpl : DatabaseRepository {

    val db = FakeDatabase()

    override fun getTasksList(): List<Task> {
        return db.list
    }

    override fun getCategoriesList(): List<Categories> {
        return db.categories
    }

    override fun getTasksInCategory(category: Categories): List<Task> {
        return db.listTaskInCat
    }
}