package org.aaronwtlu.project


import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.Placeholder
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.getBytes
import platform.UIKit.UIColor
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.UIKit.UIImageView
import platform.Foundation.NSURLSession
import platform.Foundation.create
import platform.Foundation.dataTaskWithURL
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.downloadTaskWithURL
import platform.posix.memcpy
//import io.ktor.client.*
//import io.ktor.client.call.*
//import io.ktor.client.request.*
//import io.ktor.client.statement.*
import kotlinx.cinterop.memScoped
import platform.Foundation.dataUsingEncoding

//class AntherSwifterImageDownloader {
//    private val client = HttpClient()
//    actual suspend fun downloadImage(url: String): ByteArray? {
//        return try {
//            val response: HttpResponse = client.get(url)
//            response.readBytes()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//}

//@OptIn(ExperimentalForeignApi::class)
//fun ByteArray.toImageBytes(): NSData? = memScoped {
//    val string = NSString.create(string = this@toImageBytes.decodeToString())
//    return string.dataUsingEncoding(NSUTF8StringEncoding)
//}

//class IOSImageDisplay {
//    @OptIn(ExperimentalForeignApi::class)
//    @Composable
//    fun DisplayImage(imageUrl: String) {
//        var imageData by remember { mutableStateOf<NSData?>(null) }
//        val imageDownloader = AntherSwifterImageDownloader()
//
//        LaunchedEffect(imageUrl) {
//            val byteArray = imageDownloader.downloadImage(imageUrl)
//            byteArray?.let {
//                imageData = it.toImageBytes()
//            }
//        }
//
//        imageData?.let {
////            UIImageView(image = it)
//        }
//    }
//}

//@OptIn(ExperimentalForeignApi::class)
//fun NSData.toByteArray(): ByteArray {
//    return ByteArray(length.toInt()).apply {
//        usePinned {
//            memcpy(it.addressOf(0), bytes, length)
//        }
//    }
//}
//
//fun UIImage.toData(): NSData? {
//    return UIImagePNGRepresentation(this)
//}

//fun ByteArray.toNSData(): NSData = memScoped {
//    NSData.create(bytes = allocArrayOf(this@toNSData), length = this@toNSData.size.toULong())
//}
//@OptIn(ExperimentalForeignApi::class)
//fun NSData.toByteArray(): ByteArray {
//    val point = COpaquePointer
////    val byteArray = ByteArray(this.length.toInt())
//    this.getBytes(point, length = this.length)
//    return byteArray
//}
//

class IOSImageLoader : ImageLoader {

    @Composable
    override fun loadImage(url: String, contentDescription: String?) {
        UIImage()
    }
//
//    fun download() {
//        val task = NSURLSession.sharedSession.dataTaskWithURL(url = NSURL("\"https://picsum.photos/id/57/2448/3264\"")) { nsData, nsurlResponse, nsError ->
//            nsData?.let {
//                ImageBitmap.let {  }
//            }
//        }
//        task.resume()
//    }

//    func downloadImage(url: URL, completion: (UIImage?) -> Void) {
//        let task = URLSession.shared.dataTask(with: url) { data, response, error in
////            if let data = data, error == nil {
////                print("Error downloading image: \(String(describing: error))")
////                    completion(nil)
////                return
////            }
////
////            let image = UIImage(data: data)
////            DispatchQueue.main.async {
////                completion(image)
////            }
//        }
//        task.resume()
//    }

    @Composable
    override fun loadImage(
        url: String,
        modifier: Modifier,
        contentDescription: String?,
        placeholder: Painter?
    ) {
//        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        // Draw the content.
//        val sizeResolver = request.sizeResolver
        Text("hello", modifier = modifier)
//        Image(
//            contentDescription = null,
//            modifier = modifier
//        )
//        Content(
//            modifier = if (sizeResolver is ConstraintsSizeResolver) {
//                modifier.then(sizeResolver)
//            } else {
//                modifier
//            },
//            painter = painter,
//            contentDescription = contentDescription,
//            alignment = alignment,
//            contentScale = contentScale,
//            alpha = alpha,
//            colorFilter = colorFilter,
//            clipToBounds = clipToBounds,
//        )
//        imageView.backgroundColor = UIColor.redColor()
//        imageView
//        SwiftImageLoaderProvider.imageLoader()
    }
}