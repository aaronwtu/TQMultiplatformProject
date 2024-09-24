package org.aaronwtlu.project.previewer

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material.Text
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.tooling.preview.Preview
import org.aaronwtlu.project.imageviewer.TopLayout
import org.aaronwtlu.project.imageviewer.icon.IconCustomArrowBack
import org.aaronwtlu.project.imageviewer.view.BackButton
import org.aaronwtlu.project.imageviewer.view.CircularButton

// 定义一个 CompositionLocal，用于共享字符串
val LocalExample = compositionLocalOf { "Default Value" }
// 定义另一个 CompositionLocal，用于共享整数
val LocalIntExample = compositionLocalOf { 0 }
const val PreTag = "Previewer"

@Composable
fun ExampleProvider() {
    // 使用 CompositionLocalProvider 提供一个新的值
    CompositionLocalProvider(
        LocalExample provides "Provided Value",
        LocalIntExample provides 42
    ) {
        // 在这个作用域内，LocalExample 的值将是 "Provided Value"
        TopLayout(
            { ExampleConsumer() },
            {
                ExampleRight("[Icon]")
                CircularButton(
                    imageVector = IconCustomArrowBack,
                    onClick = {
                        Log.i(PreTag,"clicked back")
                    }
                )
                ExampleRight("[Image]")
                CircularButton(content = { ExampleRight("Click") }, enabled = true, onClick = {
                    Log.i(PreTag, "clicked circular")
                })
            }
        )
    }
}

@Composable
fun ExampleRight(content: String) {
    Text("$content")
}

@Composable
fun ExampleConsumer() {
    // 使用 LocalExample.current 获取当前的值
    val stringValue = LocalExample.current
    val intValue = LocalIntExample.current
    Text(text = "String: $stringValue, Int: $intValue")
}

@Preview
@Composable
fun CompositionLocalProviderPreviewExample() {
    ExampleProvider()
}