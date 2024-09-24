package org.aaronwtlu.project

import org.aaronwtlu.project.getLogger
import org.koin.core.logger.Level

private var innerlog = getLogger()

class Klog {
    companion object {
        fun i(msg: String) {
            innerlog.log(Level.INFO, msg)
        }
        fun e(msg: String) {
            innerlog.log(Level.ERROR, msg)
        }
    }
}