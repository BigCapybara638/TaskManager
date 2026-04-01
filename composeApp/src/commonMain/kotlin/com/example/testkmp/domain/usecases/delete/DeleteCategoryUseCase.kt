package com.example.testkmp.domain.usecases.delete

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.repositories.DatabaseRepository

class DeleteCategoryUseCase(
    private val databaseRepository: DatabaseRepository
) {

    suspend operator fun invoke(
        category: Categories
    ) {
        return databaseRepository.deleteCategory(category)
    }

}