package com.example.testkmp.domain.repositories

import io.github.jan.supabase.auth.user.UserInfo
import com.example.testkmp.domain.models.Result

interface AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        username: String? = null
    ) : Result<UserInfo?>

    suspend fun signIn(
        email: String,
        password: String,
    ) : Result<UserInfo?>

    suspend fun signOut() : Result<UserInfo?>

}