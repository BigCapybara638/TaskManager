package com.example.testkmp.presentation.components.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.BackgroundColor
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.SecondaryTextColor
import com.example.testkmp.domain.models.Categories
import com.example.testkmp.domain.models.Reminder
import com.example.testkmp.domain.models.Task
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.components.dialogs.AddTaskDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriesItem(
    viewModel: HomeViewModel,
    category: Categories,
    tasksList: List<Task>,
    modifier: Modifier,
    onConfirm: (String, String?) -> Unit
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    var state by rememberSaveable { mutableStateOf(false) }
    var showAddTaskDialog by remember { mutableStateOf(false) }

    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(15.dp))
            .combinedClickable(
                onClick = {
                    state = !state
                },
                onLongClick = {
                    showMenu = !showMenu
                }
            )
            .background(Color.White)
            .padding(10.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = category.name,
                color = PrimaryTextColor,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 10.dp)
            )

            Text(
                text = " + ",
                color = SecondaryTextColor,
                fontSize = 26.sp,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        showAddTaskDialog = true
                    }
            )
        }

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
                viewModel = viewModel,
                tasks = tasksList
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
                    showMenu = false
                }
            )
            DropdownMenuItem(
                modifier = Modifier.clip(RoundedCornerShape(14.dp)),
                text = {
                    Text("Удалить", color = Color.Red)
                },
                onClick = {
                    viewModel.deleteCategory(category)
                    showMenu = false
                }
            )
        }
        if (showAddTaskDialog) {
            AddTaskDialog(
                onDismiss = { showAddTaskDialog = false },
                onConfirm = { title, description ->
                    onConfirm(title, description)
                    showAddTaskDialog = false
                }
            )
        }
    }
}

@Composable
fun TasksListContent(
    viewModel: HomeViewModel,
    tasks: List<Task>,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(6.dp))

        if (tasks.isEmpty()) {
            EmptyTasksPlaceholder()
        } else {
            //ReminderItem(Reminder(name = "Яндекс стажировка", deadline = "2026-09-02", category_id = 3L, user_id = ""), viewModel)
            tasks.forEach { task ->
                TaskItem(
                    viewModel = viewModel,
                    task = task,
                    onClick = {
                        viewModel.updateIsCompletedState(task)
                    }
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
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