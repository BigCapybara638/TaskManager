package com.example.testkmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


//val Purple80 = Color(0xFFEEB509)
val Purple80 = Color(0xFFC96E4A)

val PurpleGrey80 = Color(0xFFE3EC03)
val Pink80 = Color(0xFF5FE816)

val Pink90 = Color(0xFF164AE8)

//val BackgroundColor = Color(0xFFFDF6D2)
val BackgroundColor = Color(0xFFEAF0F3)

val SecondBackgroundColor = Color(0xFFDAF0FA)

//val ActionButtonColor = Color(0xFFF6E06C)
val ActionButtonColor = Color(0xFFC96E4A)
val EnabledActionButtonColor = Color(0xFF9E9D9D)

val PrimaryTextColor = Color(0xFF1A2B3C)
val IslandColor = Color(0xFFF1F8FF)
val SecondaryTextColor = Color(0xFF4F84BA)



private val myColorScheme = lightColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = BackgroundColor
)

@Composable
fun TaskManagerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = myColorScheme,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize()
            ) {
                content()
            }
        }
    )
}