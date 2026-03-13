package com.example.testkmp.domain.usecases

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.repositories.DatabaseRepository

class AddCategoryUseCase(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(
        category: Categories
    ) {
        return repository.addCategory(category)
    }

}