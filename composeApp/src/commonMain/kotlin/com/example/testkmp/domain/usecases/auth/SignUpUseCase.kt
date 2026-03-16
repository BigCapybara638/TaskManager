package com.example.testkmp.domain.usecases.auth

import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.user.UserInfo
import com.example.testkmp.domain.models.Result

class SignUpUseCase(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        pass: String,
        username: String? = null
    ) : Result<UserInfo?> {
        return authRepository.signUp(email, pass, username)
    }

}