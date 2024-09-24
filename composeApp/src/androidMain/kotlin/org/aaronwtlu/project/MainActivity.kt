package org.aaronwtlu.project

import android.os.Bundle
import android.util.Log
import org.aaronwtlu.project.di.appModule
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.aaronwtlu.project.Coroutine.CoroutinePreview
import org.aaronwtlu.project.imageviewer.ExternalImageViewerEvent
import org.aaronwtlu.project.imageviewer.ImageViewerAndroid
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.core.context.startKoin
//import org.koin.java.KoinJavaComponent.inject
//import org.koin.core.component.inject
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

    // TODO: CommonMain 中如何获取 service？ 
    // TODO: 非 Activity 中如何获取？ 
    // TODO: Componse 中如何获取？
    val networkService: NetworkServiceIMP by inject()
    val externalEvents = MutableSharedFlow<ExternalImageViewerEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Info","==============> start")
        startKoin {
            logger(getLogger())
            androidContext(this@MainActivity)
            modules(appModule())
            print(networkService.name())
            Log.i("Info", networkService.name())
        }
        setContent {
//            CoroutinePreview()
            ImageViewerAndroid(externalEvents)
//            App()
        }
        Log.i("Info","==============> end")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//
//    LaunchedEffect("hello") {
//        loadKoinModules(appModule())
//    }
//    App()
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoroutinePreview()
//    Text("hello")
}
