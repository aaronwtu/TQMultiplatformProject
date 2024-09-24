package org.aaronwtlu.project.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.aaronwtlu.project.Klog

class AppLifecycleObserver : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Klog.i("AppLifecycleObserver", "App Created")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Klog.i("AppLifecycleObserver", "App Started")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Klog.i("AppLifecycleObserver", "App Resumed")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Klog.i("AppLifecycleObserver", "App Paused")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Klog.i("AppLifecycleObserver", "App Stopped")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Klog.i("AppLifecycleObserver", "App Destroyed")
    }
}