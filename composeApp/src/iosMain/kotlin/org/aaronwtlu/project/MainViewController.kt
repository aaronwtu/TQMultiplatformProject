package org.aaronwtlu.project

import ImageViewerIos
import androidx.compose.material.Text
import androidx.compose.ui.window.ComposeUIViewController
import org.aaronwtlu.project.lifecycle.AppLifecycle

fun MainViewController() = ComposeUIViewController {
    AppLifecycle(
        content = {
            ImageViewerIos()
        },
        eventHandler = {
            Klog.d("Did recerived evennt: $it")
        }
    )
}
