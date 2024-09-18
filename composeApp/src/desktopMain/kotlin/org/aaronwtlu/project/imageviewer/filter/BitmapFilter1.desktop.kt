package org.aaronwtlu.project.imageviewer.filter

import androidx.compose.ui.graphics.ImageBitmap

actual fun grayScaleFilter(
    bitmap: ImageBitmap,
    context: PlatformContext
): ImageBitmap {
    TODO("Not yet implemented")
}

actual fun pixelFilter(
    bitmap: ImageBitmap,
    context: PlatformContext
): ImageBitmap {
    TODO("Not yet implemented")
}

actual fun blurFilter(
    bitmap: ImageBitmap,
    context: PlatformContext
): ImageBitmap {
    TODO("Not yet implemented")
}

actual class PlatformContext

@Composable
actual fun getPlatformContext(): PlatformContext {
    TODO("Not yet implemented")
}