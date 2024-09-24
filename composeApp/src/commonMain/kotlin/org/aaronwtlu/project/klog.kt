package org.aaronwtlu.project

import org.koin.core.logger.Level

private var innerlog = getLogger()

class Klog {
    companion object {
        fun i(tag: String, msg: String) {
            innerlog.log(Level.INFO, "[$tag] $msg")
        }

        fun i(msg: String) {
            innerlog.log(Level.INFO, msg)
        }
        fun e(msg: String) {
            innerlog.log(Level.ERROR, msg)
        }
        fun d(msg: String) {
            innerlog.log(Level.DEBUG, msg)
        }
        fun d(tag: String, msg: String) {
            innerlog.log(Level.DEBUG, "[$tag] $msg")
        }

    }
}

fun testAAAAA() {
    Klog.d(msg = "message")
}
