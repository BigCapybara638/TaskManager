package com.example.testkmp.domain.usecases.update

import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository

class UpdateTaskUseCase(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(
        task: Task
    ) {
        return databaseRepository.updateTask(task)
    }
}