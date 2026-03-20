package com.example.testkmp.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.TaskManagerTheme
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.presentation.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesItem(
    cats: Categories,
    tasksList: List<Task>,
    onClick: () -> Unit
) {

    var state by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable{
                state = !state
            }
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(text = cats.name,
            color = PrimaryTextColor,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(start = 10.dp)
        )

        AnimatedVisibility(
            visible = state,
            enter = fadeIn(
                animationSpec = tween(300)
            ) + expandVertically(
                animationSpec = tween(300)
            ),
            exit = fadeOut(
                animationSpec = tween(200)
            ) + shrinkVertically(
                animationSpec = tween(200)
            )
        ) {
            TasksListContent(
                tasks = tasksList
            )
        }
    }
}

@Composable
fun TasksListContent(
    tasks: List<Task>,
) {
    var showAddTaskDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Кнопка добавления задачи
        Button(
            modifier = Modifier
                .fillMaxWidth(0.8F),

            onClick = { showAddTaskDialog = true }
        ) {
            Text("Добавить задачу")
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Список задач
        if (tasks.isEmpty()) {
            EmptyTasksPlaceholder()
        } else {
            tasks.forEach { task ->
                TaskItem(
                    task = task,
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }

    // Диалог добавления задачи
    if (showAddTaskDialog) {
        AddTaskDialog(
            onDismiss = { showAddTaskDialog = false },
            onConfirm = { title, description ->
                //viewModel.addTask(categoryId, title, description)
                showAddTaskDialog = false
            }
        )
    }
}


@Composable
fun EmptyTasksPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Нет задач",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}