package org.aaronwtlu.project.imageviewer.view

import androidx.compose.runtime.mutableStateListOf
import org.aaronwtlu.project.Klog
import org.koin.core.logger.Level

// TODO: vararg 是什么意思？
class NavigationStack<T>(vararg initial: T) {
    // TODO: *initial 如何理解
    val stack = mutableStateListOf(*initial)
    fun push(t: T) {
        Klog.i("navi stack push $t")
        stack.add(t)
    }

    fun back() {
        if (stack.size > 1) {
            // Always keep one element on the view stack
            stack.removeLast()
        }
    }

    fun reset() {
        stack.removeRange(1, stack.size)
    }

    fun lastWithIndex() = stack.withIndex().last()
}