package org.aaronwtlu.project

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.DialogProperties

@Composable
fun alert(showDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = {
            showDialog.value = false
        },
        title = {
            Text(text = "警告")
        },
        text = {
            Text("这是一个警告对话框。")
        },
        confirmButton = {
            Button(onClick = { showDialog.value = false }) {
                Text("确定")
            }
        },
        dismissButton = {
            Button(onClick = { showDialog.value = false }) {
                Text("取消")
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}