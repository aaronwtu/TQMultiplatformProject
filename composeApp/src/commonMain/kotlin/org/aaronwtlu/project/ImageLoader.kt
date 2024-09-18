package org.aaronwtlu.project
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.Placeholder

interface ImageLoader {
    @Composable
    fun loadImage(url: String, contentDescription: String?)

    @Composable
    fun loadImage(url: String, modifier: Modifier, contentDescription: String?, placeholder: Painter?)
}