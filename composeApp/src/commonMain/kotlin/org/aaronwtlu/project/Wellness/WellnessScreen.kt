package org.aaronwtlu.project.Wellness

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aaronwtlu.project.Klog

import androidx.lifecycle.ViewModel
import kotlinx.datetime.LocalTime
import org.aaronwtlu.project.Wellness.redux.AddTask
import org.aaronwtlu.project.Wellness.redux.RemoteTask


@Composable
fun WaterCounter(modifier: Modifier = Modifier, viewModel: WellnessViewModel) {
    var count by rememberSaveable { mutableStateOf(0) }
    var showTask by rememberSaveable { mutableStateOf(true) }
    StatelessCounter(
        viewModel,
        onCreate = {
            val id = viewModel.tasks.maxOfOrNull { it.id }?.plus(1) ?: 0
            val task = WellnessTask(id = id,"Task#${LocalTime.toString()}", false)
            viewModel.store.dispatch(AddTask(task))
        },
        modifier
    )
}

// 状态上升
@Composable
fun StatelessCounter(
    viewModel: WellnessViewModel,
    onCreate: () -> Unit,
    modifier: Modifier
) {
    var isChecked by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier.padding(16.dp)) {

        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = onCreate) {
                Text("Add one")
            }
            if (!viewModel.editAble) {
                Button(onClick = { viewModel.editAble = true }) {
                    Text("Editing")
                }
            } else {
                Button(onClick = { viewModel.editAble = false }) {
                    Text("Cancel", style = TextStyle(color = Color.Red))
                }
            }
        }
        WellnessTasksList(model = viewModel, onCloseTask = { viewModel.store.dispatch(RemoteTask(it.id)) })
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
