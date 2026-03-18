package com.example.testkmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkmp.domain.usecases.auth.SignUpUseCase
import kotlinx.coroutines.launch
import com.example.testkmp.domain.models.Result
import com.example.testkmp.domain.usecases.auth.CheckAuthorizationState
import com.example.testkmp.domain.usecases.auth.SignInUseCase
import com.example.testkmp.domain.usecases.auth.SignOutUseCase
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(
    private val checkAuthorizationState: CheckAuthorizationState,
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private var _authState = MutableStateFlow<Result<UserInfo?>>(Result.Loading)
    val authState: StateFlow<Result<UserInfo?>> = _authState

    private var _startAuthState = MutableStateFlow<SessionStatus>(SessionStatus.Initializing)
    val startAuthState: StateFlow<SessionStatus> = _startAuthState

    fun checkAuthState() {
        viewModelScope.launch {
            try {
                val result = checkAuthorizationState()
                _startAuthState.value = result
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    fun signUp(email: String, pass: String, username: String? = null) {
        viewModelScope.launch {
            val result = signUpUseCase(email, pass, username)

            when(result) {
                is Result.Success -> {println("success")}
                is Result.Error -> {println(result.error)}
                is Result.Loading -> {println("loading")}
            }

        }
    }

    fun signIn(email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = Result.Loading
            try {
                val result = signInUseCase(email, pass)
                _authState.value = result
            } catch (e: Exception) {
                _authState.value = Result.Error(e.message.toString())
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                signOutUseCase()
                _authState.value = Result.Loading
            } catch (e: Exception) {
                _authState.value = Result.Error(e.message.toString())
            }
        }
    }
}
