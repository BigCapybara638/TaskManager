package com.example.testkmp.domain.usecases.auth

import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

class CheckAuthorizationState(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() : SessionStatus {
        return authRepository.checkAuthorizationState()
    }

}