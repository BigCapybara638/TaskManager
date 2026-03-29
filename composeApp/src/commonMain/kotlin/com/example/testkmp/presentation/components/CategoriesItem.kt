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
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.TaskManagerTheme
import com.example.testkmp.data.supabase
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Task
import com.example.testkmp.presentation.HomeViewModel
import io.github.jan.supabase.auth.auth
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesItem(
    userId: String,
    cats: Categories,
    tasksList: List<Task>,
    onClick: () -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()

    var state by rememberSaveable { mutableStateOf(false) }

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
                userId = userId,
                categories = cats,
                tasks = tasksList
            )
        }
    }
}

@Composable
fun TasksListContent(
    userId: String,
    categories: Categories,
    tasks: List<Task>,
) {
    var showAddTaskDialog by remember { mutableStateOf(false) }
    val viewModel: HomeViewModel = koinViewModel()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.65F)
                .padding(end = 2.dp),

            onClick = { showAddTaskDialog = true }
        ) {
            Text("Добавить задачу")
        }

        Spacer(modifier = Modifier.height(6.dp))

        val completedTasks = tasks.filter { it.completed }
        val notCompletedTasks = tasks.filter { !it.completed }

        if (tasks.isEmpty()) {
            EmptyTasksPlaceholder()
        } else {
            notCompletedTasks.forEach { task ->
                TaskItem(
                    task = task,
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            completedTasks.forEach { task ->
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
                viewModel.addTask(Task(name = title, description = description, category_id = categories.id!!, user_id = userId))
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