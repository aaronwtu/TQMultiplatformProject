package org.aaronwtlu.project.imageviewer.redux.reducers

import org.aaronwtlu.project.imageviewer.redux.state.ImageViewerState


fun rootReducer(state: ImageViewerState, action: Any) = ImageViewerState(
    imageOperationState = imageReducer(state.imageOperationState, action)
)