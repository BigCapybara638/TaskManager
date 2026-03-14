package com.example.testkmp.domain.repositories

import io.github.jan.supabase.auth.user.UserInfo

interface AuthRepository {

    suspend fun signUp() : UserInfo?

    suspend fun signIn() : UserInfo?

    suspend fun signOut() : UserInfo?

}