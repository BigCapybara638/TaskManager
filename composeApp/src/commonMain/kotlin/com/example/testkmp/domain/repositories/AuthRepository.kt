package com.example.testkmp.domain.repositories

import io.github.jan.supabase.auth.user.UserInfo
import com.example.testkmp.domain.models.Result
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun checkAuthorizationState() : SessionStatus

    suspend fun signUp(
        email: String,
        password: String,
        username: String? = null
    ) : Result<UserInfo?>

    suspend fun signIn(
        email: String,
        password: String,
    ) : Result<UserInfo?>

    suspend fun signOut() : Boolean

}