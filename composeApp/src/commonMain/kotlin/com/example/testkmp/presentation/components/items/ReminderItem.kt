package com.example.testkmp.presentation.components.items

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import com.example.testkmp.BackgroundColor
import com.example.testkmp.PrimaryTextColor
import com.example.testkmp.SecondaryTextColor
import com.example.testkmp.domain.models.Reminder
import com.example.testkmp.presentation.HomeViewModel
import com.example.testkmp.presentation.components.dialogs.UpdateTaskDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReminderItem(
    reminder: Reminder
) {
    val viewModel: HomeViewModel = koinViewModel()

    var checkedState by rememberSaveable { mutableStateOf(false) }

    var isPressed by rememberSaveable  { mutableStateOf(false) }

    var showMenu by rememberSaveable { mutableStateOf(false) }
    var showUpdateDialog by rememberSaveable { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "scale"
    )

    var isAnimating by rememberSaveable  { mutableStateOf(false) }

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
            .combinedClickable(
                onClick = {
//                    viewModel.updateIsCompletedState(task)
                    checkedState = !checkedState
                    isPressed = !isPressed
                    isAnimating = !isAnimating},
                onLongClick = {
                    showMenu = !showMenu
                }
            )
            .background(Color.White)
            .padding(2.dp)
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

        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 2.dp)
        ) {
            Text(text = reminder.deadline,
                fontSize = 10.sp,
                color = SecondaryTextColor,
                modifier = Modifier
                    .padding(start = 12.dp)
            )

            Text(text = reminder.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 10.dp)
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
