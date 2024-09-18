package org.aaronwtlu.project.imageviewer

import org.aaronwtlu.project.ioDispatcher

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.aaronwtlu.project.imageviewer.model.PictureData
import org.aaronwtlu.project.imageviewer.store.AndroidImageStorage
import org.aaronwtlu.project.imageviewer.filter.PlatformContext
import org.aaronwtlu.project.imageviewer.style.ImageViewerTheme


@Composable
fun ImageViewerAndroid(externalEvents: Flow<ExternalImageViewerEvent>) {

    val context: Context = LocalContext.current

    // 用于在 Composable 函数中创建一个 CoroutineScope 的方法。
    // 这个 CoroutineScope 的生命周期与 Composable 的生命周期绑定，
    // 当 Composable 进入 Composition 时创建，在离开 Composition 时取消。
    // 管理协程生命周期：确保协程的生命周期与 Composable 的生命周期一致，从而避免内存泄漏或意外的协程取消。
    // 简化协程使用：在 Composable 中简化协程的使用，避免手动管理 CoroutineScope。
    val ioScope = rememberCoroutineScope { ioDispatcher }
    val dependencies = remember(context, ioScope) {
        getDependencies(context, ioScope, externalEvents)
    }

    Log.i("imageviewer", "$dependencies")
    ImageViewerTheme {
        ImageViewerCommon(dependencies)
    }

//    Column {
//        // TODO: 包起来的意义是啥？
//        ImageViewerTheme {
//            Text("hello world!!",
//                color = MaterialTheme.colors.primary,
//                modifier = Modifier,
//                fontSize = 30.sp)
//        }
//        Text("Hello Kotlin!!!",
//            color = MaterialTheme.colors.error,
//            fontSize = 30.sp)
//    }
}


private fun getDependencies(
    context: Context,
    ioScope: CoroutineScope,
    externalEvents: Flow<ExternalImageViewerEvent>
) = object : Dependencies() {
    override val notification: Notification = object : PopupNotification(localization) {
        override fun showPopUpMessage(text: String) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }
    override val imageStorage: AndroidImageStorage = AndroidImageStorage(pictures, ioScope, context)
    override val sharePicture: SharePicture = object : SharePicture {
        override fun share(context: PlatformContext, picture: PictureData) {
            ioScope.launch {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_STREAM,
                        imageStorage.getUri(context.androidContext, picture)
                    )
                    putExtra(
                        Intent.EXTRA_TEXT,
                        picture.description
                    )
                    type = "image/jpeg"
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                withContext(Dispatchers.Main) {
                    context.androidContext.startActivity(Intent.createChooser(shareIntent, null))
                }
            }
        }
    }
    override val externalEvents: Flow<ExternalImageViewerEvent> = externalEvents
}
