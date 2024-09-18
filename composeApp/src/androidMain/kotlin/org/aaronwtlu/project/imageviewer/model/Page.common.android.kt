package org.aaronwtlu.project.imageviewer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
//
// 用于简化在 Android 中实现 Parcelable 接口的过程。
// Parcelable 接口用于在 Android 中在不同组件（如 Activities 和 Fragments）之间传递对象。

@Parcelize
actual class MemoryPage actual constructor(actual val pictureIndex: Int) : Page, Parcelable

@Parcelize
actual class CameraPage : Page, Parcelable

@Parcelize
actual class FullScreenPage actual constructor(actual val pictureIndex: Int) : Page, Parcelable

@Parcelize
actual class GalleryPage : Page, Parcelable
