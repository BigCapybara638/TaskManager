package com.example.testkmp.domain.usecases

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository

class GetTasksInCategoryUseCase(
    private val repository: DatabaseRepository
) {

    operator fun invoke(category: Categories) : List<Task> {
        return repository.getTasksInCategory(category)
    }

}