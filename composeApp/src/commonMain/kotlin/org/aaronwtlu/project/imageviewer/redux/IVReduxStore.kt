package org.aaronwtlu.project.imageviewer.redux

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.PlatformStorableImage
import org.aaronwtlu.project.imageviewer.ExternalImageViewerEvent
import org.aaronwtlu.project.imageviewer.ImageProvider
import org.aaronwtlu.project.imageviewer.ImageStorage
import org.aaronwtlu.project.imageviewer.Localization
import org.aaronwtlu.project.imageviewer.Notification
import org.aaronwtlu.project.imageviewer.SharePicture
import org.aaronwtlu.project.imageviewer.getCurrentLocalization
import org.aaronwtlu.project.imageviewer.model.PictureData
import org.aaronwtlu.project.imageviewer.redux.reducers.rootReducer
import org.aaronwtlu.project.imageviewer.redux.state.ImageViewerState
import org.aaronwtlu.project.imageviewer.resourcePictures
import org.aaronwtlu.project.imageviewer.toImageBitmap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.reduxkotlin.Store
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.middleware
import org.reduxkotlin.threadsafe.createThreadSafeStore
import tqmultiplatformproject.composeapp.generated.resources.Res

interface ImageViewerStore: Store<ImageViewerState> {
}

class ImageViewerStoreIMP() {
    lateinit var reduxStore: Store<ImageViewerState>

    lateinit var storage: ImageStorage
    val notification: Notification = TODO()
    val imageStorage: ImageStorage
    val sharePicture: SharePicture
    val pictures: SnapshotStateList<PictureData> = mutableStateListOf(*resourcePictures)
    open val externalEvents: Flow<ExternalImageViewerEvent> = emptyFlow()
    val localization: Localization = getCurrentLocalization()
    @OptIn(ExperimentalResourceApi::class)
    val imageProvider: ImageProvider = object : ImageProvider {
        override suspend fun getImage(picture: PictureData): ImageBitmap = when (picture) {
            is PictureData.Resource -> {
                Res.readBytes(picture.resource).toImageBitmap()
            }

            is PictureData.Camera -> {
                imageStorage.getImage(picture)
            }
        }

        override suspend fun getThumbnail(picture: PictureData): ImageBitmap = when (picture) {
            is PictureData.Resource -> {
                Res.readBytes(picture.thumbnailResource).toImageBitmap()
            }

            is PictureData.Camera -> {
                imageStorage.getThumbnail(picture)
            }
        }

        override fun saveImage(picture: PictureData.Camera, image: PlatformStorableImage) {
            pictures.add(0, picture)
            imageStorage.saveImage(picture, image)
        }

        override fun delete(picture: PictureData) {
            pictures.remove(picture)
            if (picture is PictureData.Camera) {
                imageStorage.delete(picture)
            }
        }

        override fun edit(picture: PictureData, name: String, description: String): PictureData {
            when (picture) {
                is PictureData.Resource -> {
                    val edited = picture.copy(
                        name = name,
                        description = description,
                    )
                    pictures[pictures.indexOf(picture)] = edited
                    return edited
                }

                is PictureData.Camera -> {
                    val edited = picture.copy(
                        name = name,
                        description = description,
                    )
                    pictures[pictures.indexOf(picture)] = edited
                    imageStorage.rewrite(edited)
                    return edited
                }
            }
        }
    }

}

val actionLogger = middleware<ImageViewerState> { store, next, action ->
    val result = next(action)
    Klog.i("DISPATCH action: ${action::class.simpleName}: $action")
    Klog.i("next state: ${store.state}")
    result
}

fun createImageViewerStore(): Store<ImageViewerState> {
    return createStore()
}



fun createStore(): Store<ImageViewerState> {

    return createThreadSafeStore(
        reducer = ::rootReducer,
        preloadedState = ImageViewerState(),
        enhancer = applyMiddleware(
            actionLogger
        )
    )
}