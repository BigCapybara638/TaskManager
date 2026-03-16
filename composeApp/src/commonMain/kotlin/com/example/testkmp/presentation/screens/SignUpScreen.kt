package com.example.testkmp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.TaskManagerTheme
import com.example.testkmp.presentation.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel

@Preview(showBackground = true)
@Composable
fun SignUpScreen() {
    TaskManagerTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 14.dp)

        ) {
            val viewModel: AuthViewModel = koinViewModel()

            var login by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var pass by remember { mutableStateOf("") }

            Text(
                text = "Добро пожаловать!",
                fontSize = 22.sp
            )

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Имя") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.85F)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Почта") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(0.85F)
            )

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Пароль") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(0.85F)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.3F)
                    .padding(top = 10.dp),
                onClick = {
                    if(login.isNotBlank() && pass.isNotBlank()) {
                        viewModel.signUp(email, pass, login)
                        println("fffffffffffffffffffffff")
                    }
                }
            ) {
                Text("Войти")
            }

            Text(
                text = "Уже есть аккаунт?",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = "Войти",
                fontSize = 12.sp
            )

        }
    }
}