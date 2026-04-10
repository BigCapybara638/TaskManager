package com.example.testkmp.domain.models

import io.github.jan.supabase.auth.exception.AuthErrorCode
import io.github.jan.supabase.auth.user.UserInfo

sealed class Result<out T> {

    object Loading : Result<Nothing>()
    data class Success<T>(val user: UserInfo?) : Result<T>()
    data class Error<T>(val error: String) : Result<T>()

    fun isLoading() : Boolean = this is Loading
    fun isSuccess() : Boolean = this is Success
    fun isError() : Boolean = this is Error

}
