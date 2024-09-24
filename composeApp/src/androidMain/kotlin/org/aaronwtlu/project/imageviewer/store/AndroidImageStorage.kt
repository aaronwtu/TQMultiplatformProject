package org.aaronwtlu.project.imageviewer.store

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import androidx.core.graphics.scale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.PlatformStorableImage
import org.aaronwtlu.project.imageviewer.ImageStorage
import org.aaronwtlu.project.imageviewer.model.PictureData
import org.aaronwtlu.project.imageviewer.toImageBitmap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import tqmultiplatformproject.composeapp.generated.resources.Res
import java.io.File
import java.nio.file.Files

private const val maxStorableImageSizePx = 2000
private const val storableThumbnailSizePx = 200
private const val jpegCompressionQuality = 60

class CustomException(message: String) : Exception(message)

fun validateName(name: String) {
    if (name.isBlank()) {
        throw CustomException("Name cannot be blank")
    }
    println("Valid name: $name")
}

class AndroidImageStorage(
    private val pictures: SnapshotStateList<PictureData>,
    private val ioScope: CoroutineScope,
    context: Context
) : ImageStorage {
    private val savePictureDir = File(context.filesDir, "taken_photos")
    private val sharedImagesDir = File(context.filesDir, "share_images")

    private val PictureData.Camera.jpgFile get() = File(savePictureDir, "$id.jpg")
    private val PictureData.Camera.thumbnailJpgFile
        get() = File(
            savePictureDir,
            "$id-thumbnail.jpg"
        )
    private val PictureData.Camera.jsonFile get() = File(savePictureDir, "$id.json")

    init {
        if (savePictureDir.isDirectory) {
            val files = savePictureDir.listFiles { _, name: String ->
                name.endsWith(".json")
            } ?: emptyArray()
            pictures.addAll(
                index = 0,
                elements = files.map {
                    it.readText().toCameraMetadata()
                }.sortedByDescending {
                    it.timeStampSeconds
                }
            )
        } else {
            savePictureDir.mkdirs()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun saveImage(picture: PictureData.Camera, image: PlatformStorableImage) {
        if (image.imageBitmap.width == 0 || image.imageBitmap.height == 0) {
            return
        }

        Klog.i("save Image file: ${picture.jpgFile.toPath()}")
//        ioScope.launch {
            with(image.imageBitmap) {
                picture.jpgFile.writeJpeg(fitInto(maxStorableImageSizePx))
                picture.thumbnailJpgFile.writeJpeg(fitInto(storableThumbnailSizePx))

            }
            pictures.add(0, picture)
            picture.jsonFile.writeText(picture.toJson())
//        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun delete(picture: PictureData.Camera) {
        Klog.i("delete Image file: ${picture.jpgFile.toPath()}")
//        ioScope.launch {
            if (Files.exists(picture.jsonFile.toPath())) {
                picture.jsonFile.delete()
            }
            if (Files.exists(picture.jpgFile.toPath())) {
                picture.jpgFile.delete()
            }
            if (Files.exists(picture.thumbnailJpgFile.toPath())) {
                picture.thumbnailJpgFile.delete()
            }
//        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun rewrite(picture: PictureData.Camera) {
        Klog.i("rewrite Image file: ${picture.jpgFile.toPath()}")
//        ioScope.launch {
            picture.jsonFile.delete()
            picture.jsonFile.writeText(picture.toJson())
//        }
//        withContext(ioScope.coroutineContext) {
//            picture.jsonFile.delete()
//            picture.jsonFile.writeText(picture.toJson())
//        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getThumbnail(picture: PictureData.Camera): ImageBitmap =
        withContext(ioScope.coroutineContext) {
            if (Files.exists(picture.thumbnailJpgFile.toPath())) {
             return@withContext picture.thumbnailJpgFile.readBytes().toImageBitmap()
            }
            throw CustomException("Thumbnail Picture is nil $picture")
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getImage(picture: PictureData.Camera): ImageBitmap =
        withContext(ioScope.coroutineContext) {
            if (Files.exists(picture.jpgFile.toPath())) {
                Klog.i("get Image file: ${picture.jpgFile.toPath()}")
                val bytes = picture.jpgFile.readBytes()
                // TODO: 携程中的返回需要带上相关关键字的原因，以及改表达式是注解么？ 
                return@withContext bytes.toImageBitmap()
            }
            throw CustomException("Picture is nil $picture")
        }

    // 注解 @OptIn 是用于显式声明某段代码正在使用实验性 API 的注解。
    // 实验性 API 是指那些尚未经过完整验证，可能在未来版本中发生变化或被移除的 API。
    // 可以自定义实验性API
    @OptIn(ExperimentalResourceApi::class)
    suspend fun getUri(context: Context, picture: PictureData): Uri = withContext(Dispatchers.IO) {
        if (!sharedImagesDir.exists()) {
            sharedImagesDir.mkdirs()
        }
        val tempFileToShare: File = sharedImagesDir.resolve("share_picture.jpg")
        when (picture) {
            is PictureData.Camera -> {
                picture.jpgFile.copyTo(tempFileToShare, overwrite = true)
            }

            is PictureData.Resource -> {
                if (!tempFileToShare.exists()) {
                    tempFileToShare.createNewFile()
                }
                tempFileToShare.writeBytes(Res.readBytes(picture.resource))
            }
        }
        FileProvider.getUriForFile(
            context,
            "org.aaronwtlu.project.imageviewer.fileprovider",
            tempFileToShare
        )
    }
}

private fun ImageBitmap.fitInto(px: Int): ImageBitmap {
    val targetScale = maxOf(
        px.toFloat() / width,
        px.toFloat() / height
    )
    return if (targetScale < 1.0) {
        asAndroidBitmap().scale(
            width = (width * targetScale).toInt(),
            height = (height * targetScale).toInt()
        ).asImageBitmap()
    } else {
        this
    }
}

private fun PictureData.Camera.toJson(): String =
    Json.Default.encodeToString(this)

private fun String.toCameraMetadata(): PictureData.Camera =
    Json.Default.decodeFromString(this)

private fun File.writeJpeg(image: ImageBitmap, compressionQuality: Int = jpegCompressionQuality) {
    outputStream().use {
        image.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, compressionQuality, it)
    }
}
