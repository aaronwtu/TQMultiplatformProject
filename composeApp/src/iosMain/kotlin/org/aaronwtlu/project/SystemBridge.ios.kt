package org.aaronwtlu.project

import platform.UIKit.UIDevice

class IOSSystemBridge: SystemBridge {
    override var name: String = "iOS"
}

actual fun getSystemBridge(): SystemBridge = IOSSystemBridge()