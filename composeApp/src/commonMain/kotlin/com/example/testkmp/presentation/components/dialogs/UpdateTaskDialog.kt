package com.example.testkmp.presentation.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testkmp.BackgroundColor
import com.example.testkmp.domain.models.Task
import com.example.testkmp.presentation.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UpdateTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onConfirm: (String, String?) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()

    var title by remember { mutableStateOf(task.name) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Изменение задачи") },
        containerColor = BackgroundColor,
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Название") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description ?: "",
                    onValueChange = { description = it },
                    label = { Text("Описание") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(title, description?.ifBlank { null }) },
                enabled = title.isNotBlank()
            ) {
                Text("Изменить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
//
//@Preview(showBackground = true)
//@Composable
//fun DialogPreview() {
//    UpdateTaskDialog(Task(name="Пососать", user_id = "", category_id = 1L), {}, {a,b -> println()})
//}
