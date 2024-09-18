package org.aaronwtlu.project

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.ui.platform.LocalContext

class AndroidSystemBridge : SystemBridge {
    override val name: String = "Android"
}

actual fun getSystemBridge(): SystemBridge = AndroidSystemBridge()