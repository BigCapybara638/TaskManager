package com.example.testkmp.data.repositories

import com.example.testkmp.data.network.ApiService
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.ApiRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ApiRepositoryImpl(
    private val apiService: ApiService
) : ApiRepository {

    override suspend fun getMessage(): Result<String> {
        val userTasks = getActiveTasks().toString()
        return apiService.sendMessage(userTasks)
            .map { response ->
                response.choices.firstOrNull()?.message?.content ?: ""
            }
    }

    private suspend fun getActiveTasks() : List<Task> {

        val userId = supabase.auth.currentSessionOrNull()!!.user!!.id

        return withContext(Dispatchers.IO) {
            supabase
                .from("tasks")
                .select {
                    filter {
                        Task::user_id eq userId
                        Task::completed eq false
                    }
                }
                .decodeList<Task>()
        }
    }
}