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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testkmp.IslandColor
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.SecondaryTextColor

@Composable
fun GigachatIsland() {
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
            text = "В течение дня необходимо учить английский язык, совершать физические упражнения (отжимания в 20:50 и приседания в 21:00), а также выполнить домашние дела - помыть посуду в 14:00 и забрать ребенка из садика в 12:00.",
            color = PrimaryTextColor,
            modifier = Modifier.padding(10.dp)

        )
    }
}