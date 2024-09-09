package org.aaronwtlu.project

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override var osName: String = "MacOS"
}

actual fun getPlatform(): Platform = IOSPlatform()