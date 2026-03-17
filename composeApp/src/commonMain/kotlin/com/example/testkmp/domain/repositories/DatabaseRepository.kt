package com.example.testkmp.domain.repositories

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task

interface DatabaseRepository {

    suspend fun addCategory(category: Categories)

    //suspend
    fun getTasksList() : List<Task>

    suspend fun getCategoriesList(userId: String) : List<Categories>

    fun getTasksInCategory(category: Categories) : List<Task>

}