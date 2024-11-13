package org.aaronwtlu.project.previewer

import android.os.Parcelable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.Lifecycle
import kotlinx.parcelize.Parcelize
import org.aaronwtlu.project.previewer.Foundation.*


@Composable
fun lifeCycle() {
    LaunchedEffect(Unit) {
        // 在组件创建时执行的操作
        println("Component created")
    }
}


@Preview
@Composable
fun FundationPreviewerExample() {
    Column {
        lifeCycle()
        CheckBoxStateView()
        UserInfoField()
        HelloContent()
        TextPreview()
    }
}