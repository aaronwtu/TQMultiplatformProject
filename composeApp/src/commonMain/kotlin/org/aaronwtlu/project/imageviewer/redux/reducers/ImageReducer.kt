package org.aaronwtlu.project.imageviewer.redux.reducers

import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.imageviewer.redux.action.SaveImage
import org.aaronwtlu.project.imageviewer.redux.state.ImageState
import org.aaronwtlu.project.imageviewer.redux.state.ImageViewerState


fun imageReducer(state: ImageState, action: Any) : ImageState {

    when (action) {
        is SaveImage -> Klog.i("")
    }

    return state
}