package com.example.testkmp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testkmp.di.appModule
import com.example.testkmp.presentation.screens.AuthScreen
import com.example.testkmp.presentation.screens.HomeScreen
import com.example.testkmp.presentation.screens.SignUpScreen
import org.koin.compose.KoinApplication

@Composable
fun App(modifier: Modifier) {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        SignUpScreen()
        //AuthScreen()
        //HomeScreen(modifier)
    }
}