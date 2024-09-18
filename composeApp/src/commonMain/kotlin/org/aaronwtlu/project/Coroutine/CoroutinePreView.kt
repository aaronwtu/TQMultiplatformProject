package org.aaronwtlu.project.Coroutine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CoroutinePreview() {

    var count by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    // 使用 derivedStateOf 来派生状态，确保只有 Text 组件在 count 更新时重新组合
    val countText by remember { derivedStateOf { "Count: $count" } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("remember coroutine scope Example") },
                backgroundColor = Color.LightGray
            )
        }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TODO: 在 countText 变化时，Button 进行了 recomposed，是否有方法避免？
            Text(countText)
            Button(
                onClick = {
                    coroutineScope.launch {
                        for (i in 1..5) {
                            kotlinx.coroutines.delay(1000)
                            count++
                        }
                    }
                }
            ) {
                Text("Start Counting")
            }
        }
    }
}
