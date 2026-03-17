package com.example.testkmp.data

import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import com.example.testkmp.domain.models.Result
import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.coroutines.delay

class SupabaseAuthRepositoryImpl() : AuthRepository {

    private val auth = supabase.auth

    override suspend fun signUp(
        email: String,
        password: String,
        username: String?
    ): Result<UserInfo?> {
        return try {
            when {
                email.isBlank() -> return Result.Error("Email не может быть пустым!")
                password.isBlank() -> return Result.Error("Пароль не может быть пустым!")
                password.length < 6 -> return Result.Error("Пароль должен быть длиннее 6 символов!")

            }

            val signUpResult = auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            if (signUpResult != null) {
                Result.Success(
                    UserInfo(
                        id = signUpResult.id,
                        aud = signUpResult.aud,
                        email = signUpResult.email,
                    )
                )
            } else {
                Result.Error("Не удалось создать пользователя")
            }
        } catch (e: Exception) {
            return if (e is AuthRestException)
                Result.Error("Supabase error: ${e.errorCode}")
            else
                Result.Error("Error: $e")

        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<UserInfo?> {
        return try {

            auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            val session = auth.currentSessionOrNull()
                ?: throw Exception("No session after sign up")
            println(session)

            val userInfo = UserInfo(
                id = session.user!!.id,
                email = session.user!!.email,
                aud = session.user!!.aud
            )

            Result.Success(userInfo)
        } catch (e: Exception) {
            println(e.message.toString())
            Result.Error(e.message.toString())
        }
    }

    override suspend fun signOut(): Boolean {
        try {
            val res = supabase.auth.signOut()
            println(res)
            return true
        } catch (e: Exception) {
            println(e)
            throw e
        }
    }
}