package com.example.testkmp.domain.models

import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.user.UserInfo

// Result.kt
sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val user: T?) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()

    fun isLoading(): Boolean = this is Loading
    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    inline fun onSuccess(action: (T?) -> Unit): Result<T> {
        if (this is Success) action(user)
        return this
    }

    inline fun onError(action: (String) -> Unit): Result<T> {
        if (this is Error) action(error)
        return this
    }
}