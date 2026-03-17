package com.example.testkmp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testkmp.SignUp
import com.example.testkmp.TaskManagerTheme
import com.example.testkmp.domain.models.Result
import com.example.testkmp.presentation.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Preview(showBackground = true)
@Composable
fun SignInScreen(
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    TaskManagerTheme {

        val viewModel: AuthViewModel = koinViewModel()
        val authState by viewModel.authState.collectAsState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 14.dp)

        ) {
            var login by remember { mutableStateOf("") }
            var pass by remember { mutableStateOf("") }

            Text(
                text = "Добро пожаловать!",
                fontSize = 22.sp
            )

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Логин") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85F)
            )

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Пароль") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85F)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.3F)
                    .padding(top = 10.dp),
                onClick = {
                    if(login.isNotBlank() && pass.isNotBlank()) {
                        viewModel.signIn(login, pass)
                    }
                }
            ) {
                Text("Войти")
            }

            Button(
                onClick = {
                    onNavigateToSignUp()
                }
            ) {
                Text(
                    text = "Зарегиcтрироваться",
                )
            }

            when (authState) {
                is Result.Error -> {

                }
                is Result.Loading -> {

                }
                is Result.Success -> {
                    onNavigateToHome()
                }
            }
        }
    }
}