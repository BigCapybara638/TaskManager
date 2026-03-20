package com.example.testkmp.data

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.repositories.DatabaseRepository
import com.example.testkmp.domain.models.Task
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class FakeRepositoryImpl : DatabaseRepository {

    //val db = FakeDatabase()

    override suspend fun getTasksList(userId: String): List<Task> {
        return withContext(Dispatchers.IO) {
            supabase
                .from("tasks")
                .select {
                    filter {
                        Task::user_id eq userId
                    }
                }
                .decodeList<Task>()
        }
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

    override suspend fun getTasksInCategory(category: Categories): List<Task> {
        return withContext(Dispatchers.IO) {
            supabase
                .from("tasks")
                .select {
//                    filter {
//                        Task::category_id eq category.id
//                    }
                }
                .decodeList<Task>()
        }
    }

    override suspend fun addCategory(category: Categories) {
        withContext(Dispatchers.IO) {
            supabase.from("categories").insert(category)
        }
    }

    override suspend fun addTask(task: Task) {
        withContext(Dispatchers.IO) {
            supabase.from("tasks").insert(task)
        }
    }
}