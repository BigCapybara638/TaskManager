package com.example.testkmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkmp.domain.usecases.auth.SignUpUseCase
import kotlinx.coroutines.launch
import com.example.testkmp.domain.models.Result
import com.example.testkmp.domain.usecases.auth.CheckAuthorizationStateUseCase
import com.example.testkmp.domain.usecases.auth.SignInUseCase
import com.example.testkmp.domain.usecases.auth.SignOutUseCase
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(
    private val checkAuthorizationState: CheckAuthorizationStateUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private var _authState = MutableStateFlow<Result<UserInfo?>>(Result.Loading)
    val authState: StateFlow<Result<UserInfo?>> = _authState

    private var _startAuthState = MutableStateFlow<SessionStatus>(SessionStatus.Initializing)
    val startAuthState: StateFlow<SessionStatus> = _startAuthState

    private var _signInState = MutableStateFlow<Result<UserInfo?>?>(null)
    val signInState: StateFlow<Result<UserInfo?>?> = _signInState

    private var _signUpState = MutableStateFlow<Result<UserInfo?>?>(null)
    val signUpState: StateFlow<Result<UserInfo?>?> = _signUpState

    init {
        checkAuthState()
    }

    fun checkAuthState() {
        viewModelScope.launch {
            try {
                val result = checkAuthorizationState()
                _startAuthState.value = result
            } catch (e: Exception) {
                println("Check auth error: ${e.message}")
                _startAuthState.value = SessionStatus.NotAuthenticated()
            }
        }
    }

    fun signUp(email: String, pass: String, username: String? = null) {
        viewModelScope.launch {
            _signUpState.value = Result.Loading
            val result = signUpUseCase(email, pass, username)
            _signUpState.value = result

            when (result) {
                is Result.Success -> {
                    println("Sign up success")
                    checkAuthState()
                }
                is Result.Error -> {
                    println("Sign up error: ${result.error}")
                }
                is Result.Loading -> {
                    println("Sign up loading")
                }
            }
        }
    }

    fun signIn(email: String, pass: String) {
        viewModelScope.launch {
            _signInState.value = Result.Loading
            val result = signInUseCase(email, pass)
            _signInState.value = result

            when (result) {
                is Result.Success -> {
                    println("Sign in success")
                    checkAuthState()
                }
                is Result.Error -> {
                    println("Sign in error: ${result.error}")
                }
                is Result.Loading -> {
                    println("Sign in loading")
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                signOutUseCase()
                _authState.value = Result.Loading
                _startAuthState.value = SessionStatus.NotAuthenticated()
                _signInState.value = null
                _signUpState.value = null
            } catch (e: Exception) {
                _authState.value = Result.Error(e.message.toString())
            }
        }
    }

    fun resetStates() {
        _signInState.value = null
        _signUpState.value = null
    }
}