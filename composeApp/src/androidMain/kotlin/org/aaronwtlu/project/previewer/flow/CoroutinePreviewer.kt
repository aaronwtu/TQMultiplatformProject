package org.aaronwtlu.project.previewer.flow

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.internal.wait
import org.aaronwtlu.project.Klog

val Int.seconds: Long
    get() = this.toLong() * 1000

class CoroutinePreviewer {
    companion object {
        private var instance: CoroutinePreviewer? = null

        fun getInstance(): CoroutinePreviewer {
            return instance ?: synchronized(this) {
                instance ?: CoroutinePreviewer().also { instance = it }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun test() {
        Klog.i("start end")
        runBlocking {
            simpleJobTest()
        }
//        val deferred: Deferred<Unit> = GlobalScope.async {
//            simpleJobTest()
//        }
//
//        deferred.start()
//        deferred.wait()
        Klog.i("run end")
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun simpleJobTest() {
        val job = GlobalScope.launch {
            Thread.sleep(3.seconds)
            Klog.i("job Executing!!!")
        }
        job.start()
    }
}