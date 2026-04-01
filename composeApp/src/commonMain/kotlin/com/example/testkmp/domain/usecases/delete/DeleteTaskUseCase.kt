package com.example.testkmp.domain.usecases.delete

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository

class DeleteTaskUseCase(
    private val databaseRepository: DatabaseRepository
) {

    suspend operator fun invoke(
        task: Task
    ) {
        return databaseRepository.deleteTask(task)
    }
}
