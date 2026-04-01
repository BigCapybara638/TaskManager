package com.example.testkmp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.testkmp.IslandColor
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.SecondaryTextColor
import com.example.testkmp.presentation.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GigachatIsland() {

    val viewModule: HomeViewModel = koinViewModel()
    val text = viewModule.gigachatState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.97F)
            .clip(RoundedCornerShape(20.dp))
            .background(IslandColor)
            .border(
                width = 1.dp,
                color = SecondaryTextColor,
                shape = RoundedCornerShape(20.dp))

    ) {

        Text(
            text = text.value.getOrNull() ?: "Не удалось сгенерировать ответ",
            color = PrimaryTextColor,
            modifier = Modifier.padding(10.dp)

        )
    }
}