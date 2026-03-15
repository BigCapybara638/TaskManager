package com.example.testkmp.data

import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo

class SupabaseAuthRepositoryImpl() : AuthRepository {

    private val auth = supabase.auth

    override suspend fun signUp(
        email: String,
        password: String,
        username: String?
    ): Result<UserInfo?> {
        return try {
            val signUpResult = auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            println("fffffffffffffffffffffff")
            Result.success(signUpResult)
        } catch (e: Exception) {
            println(e)
            Result.failure(e)
        }
    }

    override suspend fun signIn(): Result<UserInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<UserInfo> {
        TODO("Not yet implemented")
    }
}