package org.aaronwtlu.project.imageviewer.redux.action

import org.aaronwtlu.project.PlatformStorableImage
import org.aaronwtlu.project.imageviewer.model.PictureData


data class SaveImage(val picture: PictureData.Camera, val image: PlatformStorableImage) {}