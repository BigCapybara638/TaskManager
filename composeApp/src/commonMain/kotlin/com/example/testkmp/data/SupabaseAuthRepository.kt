package com.example.testkmp.data

import com.example.testkmp.domain.repositories.AuthRepository
import io.github.jan.supabase.auth.user.UserInfo

class SupabaseAuthRepository : AuthRepository {
    override suspend fun signUp(): UserInfo? {

    }

    override suspend fun signIn(): UserInfo? {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): UserInfo? {
        TODO("Not yet implemented")
    }
}