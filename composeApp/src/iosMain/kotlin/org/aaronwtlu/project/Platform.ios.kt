package org.aaronwtlu.project

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.aaronwtlu.project.imageviewer.icon.IconIosShare
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import platform.CoreFoundation.CFUUIDCreate
import platform.CoreFoundation.CFUUIDCreateString
import platform.Foundation.CFBridgingRelease
import platform.Foundation.NSLog
import platform.UIKit.UIDevice
import platform.Foundation.NSUUID
import platform.UIKit.UIImage
import cocoapods.SDWebImage.*

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override var osName: String = "MacOS"
    override fun imageLoader(): ImageLoader {
        return IOSImageLoader()
    }
}

class IOSStorableImage(
    val rawValue: UIImage
)

actual fun getPlatform(): Platform = IOSPlatform()
actual typealias PlatformStorableImage = IOSStorableImage

// TODO: 两个平台可以共用
actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

@OptIn(ExperimentalForeignApi::class)
//actual fun createUUID(): String = CFBridgingRelease(CFUUIDCreateString(null, CFUUIDCreate(null))) as String
actual fun createUUID(): String = NSUUID().toString()

actual val isShareFeatureSupported: Boolean = true
actual val shareIcon: ImageVector = IconIosShare

actual fun getLogger(): Logger = object : Logger(Level.DEBUG) {
    override fun display(level: Level, msg: MESSAGE) {
        NSLog("klog: $msg")
    }
}