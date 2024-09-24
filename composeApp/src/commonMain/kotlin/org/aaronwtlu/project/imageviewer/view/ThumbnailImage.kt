package org.aaronwtlu.project.imageviewer.view

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.imageviewer.LocalImageProvider
import org.aaronwtlu.project.imageviewer.model.PictureData

@Composable
fun ThumbnailImage(
    modifier: Modifier,
    picture: PictureData,
    filter: (ImageBitmap) -> ImageBitmap = remember { { it } },
) {
    val imageProvider = LocalImageProvider.current
    var imageBitmap by remember(picture) { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(picture) {
//        Klog.i("ThumbnailImage.LaunchedEffect => $picture")
        imageBitmap = imageProvider.getThumbnail(picture)
    }
//    Klog.i("ThumbnailImage => $picture")
    imageBitmap?.let {
        Image(
            bitmap = filter(it),
            contentDescription = picture.name,
            modifier = modifier,
            contentScale = ContentScale.Crop,
        )
    }
}
