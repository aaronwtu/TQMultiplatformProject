package org.aaronwtlu.project

/**
 * 1、通过协议，两端差异实现，隐藏不同点
 */
interface Platform {
    val name: String
    var osName: String
}

expect fun getPlatform(): Platform