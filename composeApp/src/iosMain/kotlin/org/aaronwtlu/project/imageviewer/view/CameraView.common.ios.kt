package org.aaronwtlu.project.imageviewer.view

import androidx.compose.ui.Modifier
import org.aaronwtlu.project.PlatformStorableImage
import org.aaronwtlu.project.imageviewer.model.PictureData
import androidx.compose.runtime.Composable

@Composable
actual fun CameraView(
    modifier: Modifier,
    onCapture: (picture: PictureData.Camera, image: PlatformStorableImage) -> Unit
) {
}
