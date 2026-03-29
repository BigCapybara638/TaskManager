package com.example.testkmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.testkmp.data.supabase
import com.example.testkmp.di.appModule
import com.example.testkmp.presentation.screens.HomeScreen
import com.example.testkmp.presentation.screens.SignUpScreen
import io.github.jan.supabase.auth.auth
import org.koin.compose.viewmodel.koinViewModel
import com.example.testkmp.presentation.AuthViewModel
import com.example.testkmp.presentation.screens.SignInScreen
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.Serializable
import org.koin.compose.KoinApplication
import com.example.testkmp.SignUp
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserSession


@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
object Home

@Composable
fun App(modifier: Modifier) {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        val authViewModel: AuthViewModel = koinViewModel()
        val navController = rememberNavController()

        val sessionStatus by authViewModel.startAuthState.collectAsState()
        println(sessionStatus)

        LaunchedEffect(Unit) {
            authViewModel.checkAuthState()
        }

        val startDestination = when (sessionStatus) {
            is SessionStatus.Authenticated -> Home
            else -> SignIn
        }

        NavHost(
            navController = navController,
            startDestination = startDestination

        ) {
            composable<Home> {
                //val home: Home = backStackEntry.toRoute()
                HomeScreen(
                    modifier = modifier,
                    onNavigateToSignIn = {
                        navController.navigate(SignIn)
                    }
                )
            }
            composable<SignIn> {
                SignInScreen(
                    onNavigateToSignUp = {
                        navController.navigate(SignUp)
                    },
                    onNavigateToHome = {
                        navController.navigate(Home)
                    }
                )
            }
            composable<SignUp> {
                SignUpScreen(
                    onNavigateToHome = {
                        navController.navigate(Home)
                    },
                    onNavigateToSignIn = {
                        navController.navigate(SignIn)
                    }
                )
            }
        }
    }
}