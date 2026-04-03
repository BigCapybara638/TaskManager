package com.example.testkmp.domain.usecases.update

import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository

class UpdateCompletedStateUseCase(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(
        task: Task
    ) {
        return repository.updateIsCompleteState(task)
    }

}