package com.example.testkmp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testkmp.domain.usecases.auth.SignUpUseCase
import kotlinx.coroutines.launch
import com.example.testkmp.domain.models.Result

class AuthViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {



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

}