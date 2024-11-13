package org.aaronwtlu.project.previewer.Foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextPreview() {
    /**
     * Update 策略
     * 根据 a、b 的变化，驱动 UI 的刷新
     * 此处需要注意性能问题：如何控制在最小 update 量
     * */
    var a: Int by remember { mutableIntStateOf(0) }
    var b: Int by remember { mutableIntStateOf(0) }

    Row {
        Column {
            Text(text = "$a",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp, color = Color.White),
                modifier = Modifier
                    .clickable { a++ }
                    .width(30.dp)
                    .border(width = 2.dp, color = Color.Red)
                    .background(Color.Black)) // A
        }
        Text(
            text = "+",
            fontSize = 20.sp,
            modifier = Modifier
                .size(30.dp)
        ) // +
        Text(text = "$b",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable { b++ }
                .border(width = 2.dp, color = Color.Blue)
                .size(30.dp)
        ) // B
        Text(
            text = "=",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .size(30.dp)
        ) // =
        Text(
            text = "${a + b}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .border(width = 2.dp, color = Color.Green)
                .size(30.dp)
        ) // C
    }
}
