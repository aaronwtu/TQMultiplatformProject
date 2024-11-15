package org.aaronwtlu.project.Wellness

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.aaronwtlu.project.Klog

@Composable
fun WellnessTaskItemA(
    taskName: String,
    onClosed: () -> Unit,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f).padding(start = 16.dp),
            text = taskName
        )
        Checkbox(checked = checked, onCheckedChange = onCheckChanged)
        IconButton(onClick = onClosed) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}

@Composable
fun FullstateWellnessTaskItem(
    task: WellnessTask,
    modifier: Modifier = Modifier,
    onClosed: () -> Unit
) {
    WellnessTaskItemA(
        taskName = task.label,
        checked = task.checked,
        onCheckChanged = { task.checked = it },
        onClosed = onClosed, // we will implement this later!
        modifier = modifier,
    )
}

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    model: WellnessViewModel,
    onCloseTask: (WellnessTask) -> Unit
) {
    var list = model.tasks
    LazyColumn(
        modifier = modifier
    ) {
        Klog.i("WellnessTasksList")
        items(list) { task ->
            FullstateWellnessTaskItem(task = task, onClosed = {
                onCloseTask(task)
                list = model.tasks
            })
        }
    }
}