package com.example.testkmp.domain.usecases

import com.example.testkmp.domain.repositories.ApiRepository

class GetMessageFromGigachatUseCase(
    private val apiRepository: ApiRepository
) {

    suspend operator fun invoke() : Result<String> {
        return apiRepository.getMessage()
    }

}