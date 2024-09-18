package org.aaronwtlu.project

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.Placeholder
import coil.compose.AsyncImage

class AndroidImageLoader : ImageLoader {
    @Composable
    override fun loadImage(url: String, contentDescription: String?) {
        AsyncImage(
            model = url,
            contentDescription = contentDescription
        )
    }

    @Composable
    override fun loadImage(
        url: String,
        modifier: Modifier,
        contentDescription: String?,
        placeholder: Painter?
    ) {
        AsyncImage(
            model = url,
            modifier = modifier,
            contentDescription = contentDescription,
            placeholder = placeholder
        )
    }
}