package org.aaronwtlu.project.Wellness

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.aaronwtlu.project.Klog

import androidx.lifecycle.ViewModel


@Composable
fun WaterCounter(modifier: Modifier = Modifier, viewModel: WellnessViewModel) {
    var count by rememberSaveable { mutableStateOf(0) }
    var showTask by rememberSaveable { mutableStateOf(true) }
    StatelessCounter(
        viewModel,
        count,
        { count++ },
        { count = 0 },
        showTask,
        onClosed = { showTask = false },
        modifier
    )
}

// 状态上升
@Composable
fun StatelessCounter(
    viewModel: WellnessViewModel,
    count: Int,
    onChanged: () -> Unit,
    onReseted: () -> Unit,
    showTask: Boolean,
    onClosed: () -> Unit,
    modifier: Modifier
) {
    var isChecked by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier.padding(16.dp)) {
        if (count > 0) {
            if (showTask) {
                WellnessTaskItemA(
                    onClosed = onClosed,
                    taskName = "Have you taken your 15 minute walk today?",
                    checked = isChecked,
                    onCheckChanged = { isChecked = it }
                )
            }
            Text("You've had $count glasses.")
        }
        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = onChanged, enabled = count < 10) {
                Text("Add one")
            }
            Button(
                onClick = onReseted,
                Modifier.padding(start = 8.dp)
            ) {
                Text("Clear water count")
            }
        }
        WellnessTasksList(model = viewModel, onCloseTask = { viewModel.remove(it) })
    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier
) {
    // TODO: Object 如何在非序列化方式下，如何保持数据转屏时不丢失
//    val viewModel = remember { WellnessViewModel() }  // 不行，还是会被清掉
//    var selectedCity = rememberSaveable(stateSaver = CitySaver) {
//        mutableStateOf(City("Madrid", "Spain"))
//    }
    val viewModel by rememberSaveable(stateSaver = WellnessViewModelSaver) { mutableStateOf(WellnessViewModel())  } // Crash
    Klog.i("WellnessScreen")
    Klog.i(viewModel.hashCode().toString())
    WaterCounter(modifier, viewModel)
}
