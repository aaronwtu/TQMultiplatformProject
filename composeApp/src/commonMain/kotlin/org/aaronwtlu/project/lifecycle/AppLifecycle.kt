package org.aaronwtlu.project.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.lifecycle.AppLifecycleObserver

@Composable
fun AppLifecycle(content: @Composable () -> Unit, eventHandler: (Lifecycle.Event) -> Unit = {}) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycle = lifecycleOwner.lifecycle
    val lifecycleObserver = remember { AppLifecycleObserver() }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            // TODO: 此处是否有类似Swift的weak strong概念？
            eventHandler(event)
            when (event) {
                Lifecycle.Event.ON_CREATE -> Klog.d("MyApp", "App Created")
                Lifecycle.Event.ON_START -> Klog.d("MyApp", "App Started")
                Lifecycle.Event.ON_RESUME -> Klog.d("MyApp", "App Resumed")
                Lifecycle.Event.ON_PAUSE -> Klog.d("MyApp", "App Paused")
                Lifecycle.Event.ON_STOP -> Klog.d("MyApp", "App Stopped")
                Lifecycle.Event.ON_DESTROY -> Klog.d("MyApp", "App Destroyed")
                else -> Unit
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    content()
}