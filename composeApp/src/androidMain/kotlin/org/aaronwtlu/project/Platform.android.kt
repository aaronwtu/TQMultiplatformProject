package org.aaronwtlu.project

import android.os.Build
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.util.UUID

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override var osName: String = "AndroidOS"
    override fun imageLoader(): ImageLoader {
        return AndroidImageLoader()
    }
}

class AndroidStorableImage(
    val imageBitmap: ImageBitmap
)

actual fun getPlatform(): Platform = AndroidPlatform()
actual val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
actual fun createUUID(): String = UUID.randomUUID().toString()
actual typealias PlatformStorableImage = AndroidStorableImage

actual val isShareFeatureSupported: Boolean = true
actual val shareIcon: ImageVector = Icons.Filled.Share