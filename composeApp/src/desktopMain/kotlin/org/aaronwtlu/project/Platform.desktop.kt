package org.aaronwtlu.project

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineDispatcher

actual val ioDispatcher: CoroutineDispatcher
    get() = TODO("Not yet implemented")

actual fun createUUID(): String {
    TODO("Not yet implemented")
}

actual class PlatformStorableImage

actual val isShareFeatureSupported: Boolean
    get() = TODO("Not yet implemented")
actual val shareIcon: ImageVector
    get() = TODO("Not yet implemented")