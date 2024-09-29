package org.aaronwtlu.project.imageviewer

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.filter
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.imageviewer.model.CameraPage
import org.aaronwtlu.project.imageviewer.model.FullScreenPage
import org.aaronwtlu.project.imageviewer.model.GalleryPage
import org.aaronwtlu.project.imageviewer.model.MemoryPage
import org.aaronwtlu.project.imageviewer.model.Page
import org.aaronwtlu.project.imageviewer.model.PictureData
import org.aaronwtlu.project.imageviewer.view.CameraScreen
import org.aaronwtlu.project.imageviewer.view.FullscreenImageScreen
import org.aaronwtlu.project.imageviewer.view.GalleryScreen
import org.aaronwtlu.project.imageviewer.view.MemoryScreen
import org.aaronwtlu.project.imageviewer.view.NavigationStack
import org.koin.core.logger.Level

const val ImageViewerTag = "ImageViewerTag"

enum class ExternalImageViewerEvent {
    Next,
    Previous,
    ReturnBack,
}

@Composable
fun ImageViewerCommon(
    dependencies: Dependencies
) {
    CompositionLocalProvider(
        LocalLocalization provides dependencies.localization,
        LocalNotification provides dependencies.notification,
        LocalImageProvider provides dependencies.imageProvider,
        LocalInternalEvents provides dependencies.externalEvents,
        LocalSharePicture provides dependencies.sharePicture,
    ) {
//        Text("hello ${dependencies.pictures}")
        ImageViewerWithProvidedDependencies(dependencies.pictures)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ImageViewerWithProvidedDependencies(
    pictures: SnapshotStateList<PictureData>
) {
    // rememberSaveable is required to properly handle Android configuration changes (such as device rotation)
    val selectedPictureIndex = rememberSaveable { mutableStateOf(0) }
    val navigationStack = rememberSaveable(
        saver = listSaver<NavigationStack<Page>, Page>(
            restore = { NavigationStack(*it.toTypedArray()) },
            save = { it.stack },
        )
    ) {
        NavigationStack(GalleryPage())
    }
    val externalEvents = LocalInternalEvents.current
    LaunchedEffect(Unit) {
        Klog.i("Launched effect in ImageViewer")
        externalEvents
            .filter { it == ExternalImageViewerEvent.ReturnBack }
            .collect {
                navigationStack.back()
            }
    }

    AnimatedContent(
        targetState = navigationStack.lastWithIndex(),
        transitionSpec = {
            val previousIdx = initialState.index
            val currentIdx = targetState.index
            Klog.i("transition => previousIdx: $previousIdx currentIdx: $currentIdx")
            val multiplier = if (previousIdx < currentIdx) 1 else -1
            if (initialState.value is GalleryPage && targetState.value is MemoryPage) {
                fadeIn() togetherWith fadeOut(tween(durationMillis = 500, 500))
            } else if (initialState.value is MemoryPage && targetState.value is GalleryPage) {
                fadeIn() togetherWith fadeOut(tween(delayMillis = 150))
            } else {
                slideInHorizontally { w -> multiplier * w } togetherWith
                        slideOutHorizontally { w -> multiplier * -1 * w }
            }
        }) { (_, page) ->
        Klog.i("did picked page $page")
        when (page) {
            is GalleryPage -> {
                GalleryScreen(
                    pictures = pictures,
                    selectedPictureIndex = selectedPictureIndex,
                    onClickPreviewPicture = { previewPictureIndex ->
                        navigationStack.push(MemoryPage(previewPictureIndex))
                    }
                ) {
                    navigationStack.push(CameraPage())
                }
            }

            is FullScreenPage -> {
                FullscreenImageScreen(
                    picture = pictures[page.pictureIndex],
                    back = {
                        navigationStack.back()
                    }
                )
            }

            is MemoryPage -> {
                MemoryScreen(
                    pictures = pictures,
                    memoryPage = page,
                    onSelectRelatedMemory = { pictureIndex ->
                        navigationStack.push(MemoryPage(pictureIndex))
                    },
                    onBack = { resetNavigation ->
                        if (resetNavigation) {
                            selectedPictureIndex.value = 0
                            navigationStack.reset()
                        } else {
                            navigationStack.back()
                        }
                    },
                    onHeaderClick = { pictureIndex ->
                        navigationStack.push(FullScreenPage(pictureIndex))
                    },
                )
            }

            is CameraPage -> {
                CameraScreen(
                    onBack = { resetSelectedPicture ->
                        if (resetSelectedPicture) {
                            selectedPictureIndex.value = 0
                        }
                        navigationStack.back()
                    },
                )
            }
        }
    }
}
