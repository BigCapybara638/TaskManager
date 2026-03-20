package com.example.testkmp.domain.usecases.auth

import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.status.SessionStatus

class CheckAuthorizationStateUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() : SessionStatus {
        return authRepository.checkAuthorizationState()
    }

}