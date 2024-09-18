package org.aaronwtlu.project.imageviewer.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.aaronwtlu.project.imageviewer.model.ScalableState

@Composable
expect fun ZoomControllerView(modifier: Modifier, scalableState: ScalableState)
