package com.example.testkmp.data.repositories

import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl : DatabaseRepository {

    //val db = FakeDatabase()

    override suspend fun getTasksList(userId: String): List<Task> {
        return withContext(Dispatchers.IO) {
            supabase
                .from("tasks")
                .select {
                    filter {
                        Task::user_id eq userId
                    }
                    order("id", Order.DESCENDING)
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

    override suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO) {
            try {
                supabase
                    .from("tasks")
                    .update(
                        mapOf(
                            "name" to task.name,
                            "description" to task.description
                        )
                    ) {
                        filter {
                            eq("id", task.id!!)
                        }
                    }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override suspend fun updateIsCompleteState(task: Task) {
        withContext(Dispatchers.IO) {
            try {
                supabase
                    .from("tasks")
                    .update(
                        {
                            set("completed", !task.completed)
                        }
                    ) {
                        filter {
                            eq("id", task.id!!)
                        }
                    }
            } catch (e: Exception) {
                throw e
            }

        }
    }

    override suspend fun addCategory(category: Categories) {
        withContext(Dispatchers.IO) {
            supabase.from("categories").insert(category)
        }
    }

    override suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO) {
            supabase.from("tasks")
                .delete {
                    filter {
                        eq("id", task.id!!)
                    }
                }
        }
    }

    override suspend fun deleteCategory(category: Categories) {
        withContext(Dispatchers.IO) {
            supabase.from("categories")
                .delete {
                    filter {
                        eq("id", category.id!!)
                    }
                }
        }
    }

    override suspend fun addTask(task: Task) {
        withContext(Dispatchers.IO) {
            supabase.from("tasks").insert(task)
        }
    }
}