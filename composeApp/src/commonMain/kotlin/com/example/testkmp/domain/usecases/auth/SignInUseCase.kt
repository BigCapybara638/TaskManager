package com.example.testkmp.domain.usecases.auth

import com.example.testkmp.domain.models.Result
import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.user.UserInfo

class SignInUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) : Result<UserInfo?> {
        return authRepository.signIn(email, password)
    }
}
