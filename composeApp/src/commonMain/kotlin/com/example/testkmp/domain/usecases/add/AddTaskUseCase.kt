package com.example.testkmp.domain.usecases.add

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository

class AddTaskUseCase(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(
        task: Task
    ) {
        return repository.addTask(task)
    }

}