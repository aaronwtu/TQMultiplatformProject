package org.aaronwtlu.project.previewer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import org.aaronwtlu.project.Klog
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.isActive

@Composable
fun LaunchedEffectPreviewer() {
    // 使用 remember 记住一个状态，确保 LaunchedEffect 只在初始组合时执行
    var count by remember { mutableIntStateOf(0) }

    // 当 count 的值发生变化时，重新执行副作用
    LaunchedEffect(count) {
        delay(1000L)
        Klog.i("LaunchedEffect is running")
        // TODO: 1、为什么会执行2次？，2、isActive的作用以及while用法怎么理解
        /***
        try {
            while (isActive) {
                delay(1000L)
                Klog.i("LaunchedEffect is running")
            }
        } finally {
            Klog.i("LaunchedEffect is cancelled")
        }*/
    }

    Column {
        Button(onClick = { count++ }) {
            Text("Increment count")
        }
        Text("LaunchedEffect Previewer")
    }
}

@Preview
@Composable
fun PreviewLaunchedEffectPreviewerExample() {
    LaunchedEffectPreviewer()
}