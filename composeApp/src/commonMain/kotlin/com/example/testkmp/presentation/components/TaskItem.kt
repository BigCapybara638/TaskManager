package com.example.testkmp.presentation.components

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Task
import com.example.testkmp.presentation.HomeViewModel
import io.github.jan.supabase.auth.auth
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskItem(task: Task) {
    val viewModel: HomeViewModel = koinViewModel()

    var checkedState by remember { mutableStateOf(task.completed) }

    var isPressed by remember { mutableStateOf(task.completed) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "scale"
    )

    var isAnimating by remember { mutableStateOf(task.completed) }

    val alpha by animateFloatAsState(
        targetValue = if (isAnimating) 0.5f else 1f,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        ),
        label = "alpha"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable{
                viewModel.updateIsCompletedState(task)
                checkedState = !checkedState
                isPressed = !isPressed
                isAnimating = !isAnimating
            }
            .background(Color.White)
            .padding(6.dp)
            .graphicsLayer {
                this.alpha = alpha
                this.scaleX = scale
            }

    ) {

        Checkbox(
            checked = checkedState,
            modifier = Modifier
                .size(40.dp),
            onCheckedChange = {
                checkedState = it
            }
        )
        Text(text = task.name,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(start = 10.dp)
        )
    }
}