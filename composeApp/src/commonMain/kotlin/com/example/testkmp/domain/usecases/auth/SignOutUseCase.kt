package com.example.testkmp.domain.usecases.auth

import com.example.testkmp.domain.models.Result
import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.user.UserInfo

class SignOutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() : Boolean {
        return authRepository.signOut()
    }
}