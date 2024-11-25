package org.aaronwtlu.project.previewer.redux

import org.aaronwtlu.project.Klog

public fun <T> compose(functions: List<(T) -> T>): (T) -> T = { x ->
    // 将 x 作为初始值，依次将列表中的函数应用于初始值
    functions.foldRight(x) { f, composed ->
        f(composed)
    }
}

fun increase(value: Int) : Int {
    return value + 1
}

fun testCompose() {
    val list = listOf(::increase, ::increase, ::increase)
    val up = compose(list)
    Klog.i("testCompose => ${up(1)}")
}

fun redux_foundation_test() {
    testCompose()
}