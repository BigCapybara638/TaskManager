package com.example.testkmp.domain.repositories

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task

interface DatabaseRepository {

    //suspend
    fun getTasksList() : List<Task>

    fun getCategoriesList() : List<Categories>

}