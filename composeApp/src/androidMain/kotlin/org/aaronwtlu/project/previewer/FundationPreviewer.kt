package org.aaronwtlu.project.previewer

import android.os.Parcelable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.Lifecycle
import kotlinx.parcelize.Parcelize
import org.aaronwtlu.project.previewer.Foundation.*
import org.aaronwtlu.project.previewer.Layout.fadedBackground
import org.aaronwtlu.project.previewer.Layout.myBackground


@Composable
fun lifeCycle() {
    LaunchedEffect(Unit) {
        // 在组件创建时执行的操作
        println("Component created")
    }
}

//@Composable
//fun MyScreen() {
//    CompositionLocalProvider(LocalContentColor provides Color.Green) {
//        // Background modifier created with green background
//        val backgroundModifier = Modifier.myBackground()
//
//        // LocalContentColor updated to red
//        CompositionLocalProvider(LocalContentColor provides Color.Red) {
//
//            // Box will have green background, not red as expected.
//            Box(modifier = backgroundModifier)
//        }
//    }
//}

@Preview
@Composable
fun FundationPreviewerExample() {
    Column {
        Row(modifier = Modifier.fadedBackground().padding(16.dp)) {
            Text("Hello modifier")
        }
//        lifeCycle()
//        CheckBoxStateView(Modifier.myBackground(Color.Red))
//        UserInfoField(Modifier.myBackground(Color.Black))
//        HelloContent()
//        TextPreview()
    }
}