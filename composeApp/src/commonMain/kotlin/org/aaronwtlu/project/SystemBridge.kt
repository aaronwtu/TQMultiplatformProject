package org.aaronwtlu.project

/**
 * 1、通过协议，两端差异实现，隐藏不同点
 */
interface SystemBridge {
    val name: String
}

expect fun getSystemBridge(): SystemBridge