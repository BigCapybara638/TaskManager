package com.example.testkmp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.presentation.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel
import com.example.testkmp.domain.models.Result

@Preview(showBackground = true)
@Composable
fun SignUpScreen(
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel: AuthViewModel = koinViewModel()
    val signUpState by viewModel.signUpState.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.resetStates()
    }

    LaunchedEffect(signUpState) {
        when (signUpState) {
            is Result.Success -> {
                viewModel.signIn(email, pass)
                onNavigateToHome()
            }

            is Result.Error -> {
                errorMessage = (signUpState as Result.Error).error
            }

            else -> {}
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 14.dp)
    ) {
        Text(
            text = "Регистрация",
            fontSize = 22.sp
        )

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = null
            },
            label = { Text("Имя пользователя") },
            singleLine = true,
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth(0.85F)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            label = { Text("Email") },
            singleLine = true,
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth(0.85F)
        )

        OutlinedTextField(
            value = pass,
            onValueChange = {
                pass = it
                errorMessage = null
            },
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = errorMessage != null,
            modifier = Modifier.fillMaxWidth(0.85F)
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            modifier = Modifier.padding(top = 10.dp),
            onClick = {
                when {
                    username.isBlank() -> errorMessage = "Введите имя пользователя"
                    email.isBlank() -> errorMessage = "Введите email"
                    !isValidEmail(email) -> errorMessage = "Введите корректный email"
                    pass.isBlank() -> errorMessage = "Введите пароль"
                    pass.length < 6 -> errorMessage = "Пароль должен быть не менее 6 символов"
                    else -> {
                        errorMessage = null
                        viewModel.signUp(email, pass, username)
                    }
                }
            }
        ) {
            if (signUpState is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Зарегистрироваться")
            }
        }

        Button(onClick = onNavigateToSignIn) {
            Text("Уже есть аккаунт? Войти")
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return email.contains('@') && email.contains('.') && email.length > 5
}