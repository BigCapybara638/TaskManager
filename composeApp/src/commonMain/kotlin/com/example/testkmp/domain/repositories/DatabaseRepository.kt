package com.example.testkmp.domain.repositories

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task

interface DatabaseRepository {

    suspend fun addCategory(category: Categories)

    suspend fun addTask(task: Task)

    suspend fun getTasksList(userId: String) : List<Task>

    suspend fun getCategoriesList(userId: String) : List<Categories>

    suspend fun getTasksInCategory(category: Categories) : List<Task>

}