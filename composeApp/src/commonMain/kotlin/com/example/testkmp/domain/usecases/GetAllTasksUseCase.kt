package com.example.testkmp.domain.usecases

import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository

class GetAllTasksUseCase(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(
        userId: String
    ) : List<Task> {
        return repository.getTasksList(userId)
    }

}