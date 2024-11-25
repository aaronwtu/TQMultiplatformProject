package org.aaronwtlu.project.previewer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.aaronwtlu.project.previewer.redux.redux_foundation_test

@Preview
@Composable
fun ReduxPreviewerExample() {
    redux_foundation_test()
    Text("hello")
}