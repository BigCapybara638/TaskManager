package com.example.testkmp.data

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.repositories.DatabaseRepository
import com.example.testkmp.domain.models.Task
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class FakeRepositoryImpl : DatabaseRepository {

    val db = FakeDatabase()

    override fun getTasksList(): List<Task> {
        return db.list
    }

    override suspend fun getCategoriesList(userId: String): List<Categories> {
        return withContext(Dispatchers.IO) {
            supabase
                .from("categories")
                .select {
                    filter {
                        Categories::userId eq userId
                    }
                }
                .decodeList<Categories>()
        }
    }

    override fun getTasksInCategory(category: Categories): List<Task> {
        return db.listTaskInCat
    }

    override suspend fun addCategory(category: Categories) {
        withContext(Dispatchers.IO) {
            supabase.from("categories").insert(category)
        }
    }
}