package com.example.testkmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkmp.domain.usecases.auth.SignUpUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    fun signUp(email: String, pass: String, username: String? = null) {
        viewModelScope.launch {
            signUpUseCase(email, pass, username)
        }
    }

}