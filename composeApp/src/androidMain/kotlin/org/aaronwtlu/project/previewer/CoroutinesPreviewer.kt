package org.aaronwtlu.project.previewer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.aaronwtlu.project.Klog

/**
 * StateFlow
 * 状态容器：StateFlow 是一个状态容器，始终持有一个最新的值。
 * 初始值：StateFlow 必须有一个初始值。
 * 重放最新值：当新的订阅者开始收集 StateFlow 时，它会立即接收到最新的值。
 * 值的更新：通过 value 属性或 emit 方法更新值。
 * 冷流：StateFlow 是冷流，只有在被收集时才会开始发射数据
 *
 * SharedFlow
 * 事件流：SharedFlow 用于表示事件流，可以发射多个值。
 * 无初始值：SharedFlow 不需要初始值。
 * 重放缓存：可以配置重放缓存的大小，允许新的订阅者接收到之前发射的值。
 * 热流：SharedFlow 是热流，即使没有订阅者也会发射数据。
 * 多播：SharedFlow 支持多播，多个订阅者可以同时接收数据。
 */

fun simpleStateFlow(): StateFlow<Int> = MutableStateFlow(0)


class CoroutinesPreviewerViewModel {
    private val _state = MutableStateFlow("Initial State")
    val state: StateFlow<String> get() = _state

    fun updateState(newState: String) {
        _state.value = newState
        // suspend
        // _state.emit(newState)
    }
}

// Flow
fun simpleFlow(): Flow<Int> = flow<Int> {
    for (i in 1..5) {
        delay(1000)
        emit(i)
    }
}


@Composable
fun CoroutinesPreviewer() {
    Text("org.aaronwtlu.project.previewer.CoroutinesPreviewer")
}

@Preview
@Composable
fun CoroutinesPreviewerExample() {

    // Simple example
    runBlocking {
        simpleFlow()
            .filter { it % 2 != 0 }
            .map { it * 2 }
            .map { ">>> $it" }
            .collect {
                Klog.i("flow value: $it")
            }
    }

    CoroutinesPreviewer()
}