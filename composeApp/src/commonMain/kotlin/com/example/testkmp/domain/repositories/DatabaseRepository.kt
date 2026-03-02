package com.example.testkmp.domain.repositories

import com.example.testkmp.domain.models.Task

interface DatabaseRepository {

    //suspend
    fun getList() : List<Task>

}