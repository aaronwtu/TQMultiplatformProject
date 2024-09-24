package org.aaronwtlu.project.previewer

import android.graphics.Paint.Align
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentPreviewer() {
    var count by remember { mutableIntStateOf(0) }

    Column {
        Button(onClick = { count++ }) {
            Text("Increment")
        }

        /**
         * 页面切换：在不同页面之间切换时添加动画效果。
         * 内容更新：在内容更新时添加动画效果，例如列表项的添加或删除。
         * 状态变化：在状态变化时添加动画效果，例如显示或隐藏某些组件。
         * */
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                // 淡入淡出
//                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                // Slider
                // TODO: togetherWith 如何进一步理解
                slideInHorizontally { width -> width } togetherWith slideOutHorizontally { width -> -width }
            },
            label = "Test"
        ) { targetCount ->
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 100.dp)
                    .background(Color.Green)
            ) {
                Text(
                    "Count: $targetCount",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color.Blue),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAnimatedContentPreviewerExample() {
    AnimatedContentPreviewer()
}