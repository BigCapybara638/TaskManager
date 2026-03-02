package com.example.testkmp.presentation

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testkmp.domain.models.Task

@Composable
fun TaskItem(task: Task) {
    var checkedState by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable{ checkedState = !checkedState }
            .background(Color.White)
            .padding(10.dp)
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