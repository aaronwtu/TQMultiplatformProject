package org.aaronwtlu.project
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.logger.Level
import org.koin.core.logger.Logger

/**
 * 1、通过协议，两端差异实现，隐藏不同点
 */
interface Platform {
    val name: String
    var osName: String
    fun imageLoader(): ImageLoader
}

expect class PlatformStorableImage

expect fun getPlatform(): Platform

expect val ioDispatcher: CoroutineDispatcher
expect fun createUUID(): String

expect val isShareFeatureSupported: Boolean
expect val shareIcon: ImageVector

expect fun getLogger(): Logger
