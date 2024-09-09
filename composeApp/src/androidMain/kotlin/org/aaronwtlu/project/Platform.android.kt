package org.aaronwtlu.project

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override var osName: String = "AndroidOS"
}

actual fun getPlatform(): Platform = AndroidPlatform()