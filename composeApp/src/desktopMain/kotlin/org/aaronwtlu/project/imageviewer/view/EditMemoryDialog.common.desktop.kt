package org.aaronwtlu.project.imageviewer.view

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable

@Composable
actual fun BoxScope.EditMemoryDialog(
    previousName: String,
    previousDescription: String,
    save: (name: String, description: String) -> Unit
) {
}