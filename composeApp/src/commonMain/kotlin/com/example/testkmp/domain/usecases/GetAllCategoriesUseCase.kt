package com.example.testkmp.domain.usecases

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.repositories.DatabaseRepository

class GetAllCategoriesUseCase(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(
        userId: String
    ) : List<Categories> {
        return repository.getCategoriesList(userId)
    }

}