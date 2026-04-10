package com.example.testkmp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testkmp.SignUp
import com.example.testkmp.TaskManagerTheme
import com.example.testkmp.domain.models.Result
import com.example.testkmp.presentation.AuthViewModel
import io.github.jan.supabase.auth.status.SessionStatus
import org.koin.compose.viewmodel.koinViewModel

@Preview(showBackground = true)
@Composable
fun SignInScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel: AuthViewModel = koinViewModel()
    val signInState by viewModel.signInState.collectAsState()
    val startAuthState by viewModel.startAuthState.collectAsState()

    var login by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.resetStates()
    }

    LaunchedEffect(signInState) {
        when (signInState) {
            is Result.Success -> {
                onNavigateToHome()
            }

            is Result.Error -> {
                errorMessage = (signInState as Result.Error).error
            }

            else -> {}
        }
    }

    LaunchedEffect(startAuthState) {
        when (startAuthState) {
            is SessionStatus.Authenticated -> {
                onNavigateToHome()
            }
            is SessionStatus.RefreshFailure -> {
                errorMessage = "Сессия истекла, войдите снова"
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
            text = "Добро пожаловать!",
            fontSize = 22.sp
        )

        OutlinedTextField(
            value = login,
            onValueChange = {
                login = it
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
            modifier = Modifier
                .fillMaxWidth(0.3F)
                .padding(top = 10.dp),
            onClick = {
                when {
                    login.isBlank() -> errorMessage = "Введите email"
                    pass.isBlank() -> errorMessage = "Введите пароль"
                    else -> {
                        errorMessage = null
                        viewModel.signIn(login, pass)
                    }
                }
            }
        ) {
            if (signInState is Result.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Войти")
            }
        }

        Button(onClick = onNavigateToSignUp) {
            Text("Зарегистрироваться")
        }
    }
}