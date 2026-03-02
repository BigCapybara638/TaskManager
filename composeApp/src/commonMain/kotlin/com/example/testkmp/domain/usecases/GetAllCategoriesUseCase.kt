package com.example.testkmp.domain.usecases

import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.domain.repositories.DatabaseRepository

class GetAllCategoriesUseCase(
    private val repository: DatabaseRepository
) {

    operator fun invoke() : List<Categories> {
        return repository.getCategoriesList()
    }

}