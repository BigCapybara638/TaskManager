package com.example.testkmp.presentation.components.items

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testkmp.BackgroundColor
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.SecondBackgroundColor
import com.example.testkmp.SecondaryTextColor
import com.example.testkmp.domain.models.Reminder
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.components.dialogs.UpdateTaskDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReminderItem(
    reminder: Reminder,
    viewModel: HomeViewModel,
    ) {

    var isPressed by rememberSaveable  { mutableStateOf(false) }

    var showMenu by rememberSaveable { mutableStateOf(false) }
    var showUpdateDialog by rememberSaveable { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        label = "scale",
        finishedListener = { isPressed = false }
    )

    var isAnimating by rememberSaveable  { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isAnimating) 0.5f else 1f,
        animationSpec = tween(250),
        label = "alpha"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .combinedClickable(
                onClick = {
//                    viewModel.updateIsCompletedState(task)
                    isPressed = !isPressed
                    isAnimating = !isAnimating
                },
                onLongClick = {
                    showMenu = true
                }
            )
            .border(1.dp, SecondBackgroundColor, RoundedCornerShape(15.dp))
            .padding(start = 2.dp)
            .graphicsLayer {
                this.alpha = alpha
                this.scaleX = scale
            }

    ) {

        Checkbox(
            checked = reminder.completed,
            modifier = Modifier
                .size(40.dp),
            onCheckedChange = null
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        ) {
            Text(text = reminder.deadline,
                fontSize = 14.sp,
                color = SecondaryTextColor,
                modifier = Modifier
                    .padding(start = 5.dp)
            )

            Text(text = reminder.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

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
                    //viewModel.deleteTask(task)
                    showMenu = false
                }
            )
        }

        if (showUpdateDialog) {

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ReminderItemPreview() {
//    val viewModel: HomeViewModel = koinViewModel()
//    ReminderItem(Reminder(name = "Яндекс", deadline = "2026-03-09", category_id = 1L, user_id = ""), viewModel)
//}