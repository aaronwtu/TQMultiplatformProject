package org.aaronwtlu.project

class AndroidSystemBridge : SystemBridge {
    override val name: String = "Android"
}

actual fun getSystemBridge(): SystemBridge = AndroidSystemBridge()