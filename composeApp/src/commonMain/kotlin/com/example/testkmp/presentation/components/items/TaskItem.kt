package com.example.testkmp.presentation.components.items

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.BackgroundColor
import com.example.testkmp.domain.models.Task
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.components.dialogs.UpdateTaskDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskItem(
    task: Task,
    viewModel: HomeViewModel,
    onClick: () -> Unit,
) {
    // диалоги и меню
    var showMenu by rememberSaveable { mutableStateOf(false) }
    var showUpdateDialog by rememberSaveable { mutableStateOf(false) }

    // анимации
    val alpha by animateFloatAsState(
        targetValue = if (task.completed) 0.5f else 1f,
        animationSpec = tween(250),
        label = "alpha"
    )

    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "scale",
        finishedListener = { isPressed = false }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .combinedClickable(
                onClick = {
                    isPressed = true
                    onClick()
                },
                onLongClick = { showMenu = true }
            )
            .graphicsLayer {
                this.alpha = alpha
                this.scaleX = scale
                this.scaleY = scale
            }
            .padding(8.dp)
    ) {
        Checkbox(
            checked = task.completed,
            onCheckedChange = null
        )

        Text(
            text = task.name,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp),
            style = TextStyle(
                textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
            )
        )

        DropdownMenu(
            expanded = showMenu,
            containerColor = BackgroundColor,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(horizontal = 10.dp),
            onDismissRequest = { showMenu = false }

        ) {
            DropdownMenuItem(
                modifier = Modifier.clip(RoundedCornerShape(14.dp)),
                text = {
                    Text("Изменить название")
                },
                onClick = {
                    showUpdateDialog = true
                    showMenu = false
                }
            )
            DropdownMenuItem(
                modifier = Modifier.clip(RoundedCornerShape(14.dp)),
                text = {
                    Text("Удалить", color = Color.Red)
                },
                onClick = {
                    viewModel.deleteTask(task)
                    showMenu = false
                }
            )
        }

        if (showUpdateDialog) {
            UpdateTaskDialog(
                task = task,
                onDismiss = { showUpdateDialog = false },
                onConfirm = { title, description ->
                    viewModel.updateTask(task)
                    showUpdateDialog = false
                }
            )
        }
    }
}