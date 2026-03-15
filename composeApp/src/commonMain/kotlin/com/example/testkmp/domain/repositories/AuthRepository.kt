package com.example.testkmp.domain.repositories

import io.github.jan.supabase.auth.user.UserInfo

interface AuthRepository {

    suspend fun signUp(
        email: String,
        password: String,
        username: String? = null
    ) : Result<UserInfo?>

    suspend fun signIn() : Result<UserInfo?>

    suspend fun signOut() : Result<UserInfo?>

}