package com.example.testkmp.data

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task

class FakeDatabase {

    val categories = listOf(
        Categories(0, name = "Домашние дела", description =  "Описание", userId = ""),
        Categories(1, name ="В колледже", description ="Описание", userId = ""),
        Categories(0, name ="На тренировке", description = "Описание", userId = ""),
    )
}