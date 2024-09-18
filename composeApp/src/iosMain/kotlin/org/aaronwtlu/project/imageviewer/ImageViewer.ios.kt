
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.aaronwtlu.project.imageviewer.Dependencies
import org.aaronwtlu.project.imageviewer.ImageViewerCommon
import org.aaronwtlu.project.imageviewer.Notification
import org.aaronwtlu.project.imageviewer.PopupNotification
import org.aaronwtlu.project.imageviewer.SharePicture
import org.aaronwtlu.project.imageviewer.filter.PlatformContext
import org.aaronwtlu.project.imageviewer.model.PictureData
import org.aaronwtlu.project.imageviewer.store.IOSImageStorage
import org.aaronwtlu.project.imageviewer.style.ImageViewerTheme
import org.aaronwtlu.project.imageviewer.view.Toast
import org.aaronwtlu.project.imageviewer.view.ToastState
import org.aaronwtlu.project.ioDispatcher
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIWindow

@Composable
internal fun ImageViewerIos() {
    val toastState = remember { mutableStateOf<ToastState>(ToastState.Hidden) }
    val ioScope: CoroutineScope = rememberCoroutineScope { ioDispatcher }
    val dependencies = remember(ioScope) {
        getDependencies(ioScope, toastState)
    }
    ImageViewerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ImageViewerCommon(
                dependencies = dependencies
            )
            Toast(toastState)
        }
    }
}

fun getDependencies(ioScope: CoroutineScope, toastState: MutableState<ToastState>) =
    object : Dependencies() {
        override val notification: Notification = object : PopupNotification(localization) {
            override fun showPopUpMessage(text: String) {
                toastState.value = ToastState.Shown(text)
            }
        }

        override val imageStorage: IOSImageStorage = IOSImageStorage(pictures, ioScope)

        override val sharePicture: SharePicture = object : SharePicture {
            override fun share(context: PlatformContext, picture: PictureData) {
                ioScope.launch {
                    imageStorage.getNSURLToShare(picture).path?.let { imageUrl ->
                        withContext(Dispatchers.Main) {
                            val window = UIApplication.sharedApplication.windows.last() as? UIWindow
                            val currentViewController = window?.rootViewController
                            val activityViewController = UIActivityViewController(
                                activityItems = listOf(
                                    UIImage.imageWithContentsOfFile(imageUrl),
                                    picture.description
                                ),
                                applicationActivities = null
                            )
                            currentViewController?.presentViewController(
                                viewControllerToPresent = activityViewController,
                                animated = true,
                                completion = null,
                            )
                        }
                    }
                }
            }
        }
    }
