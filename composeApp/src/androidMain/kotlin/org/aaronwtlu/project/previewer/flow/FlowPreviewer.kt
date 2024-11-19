package org.aaronwtlu.project.previewer.flow

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.aaronwtlu.project.Klog
import org.aaronwtlu.project.ioDispatcher

fun logCurrentThread() {
    val currentThread = Thread.currentThread()
    val threadName = currentThread.name
    Klog.i("Current thread: $threadName")
}

class FlowPreviewer private constructor() {
    /// 单例模式
    companion object {
        private var instance: FlowPreviewer? = null
        fun getInstance(): FlowPreviewer {
            return instance ?: synchronized(this) {
                instance ?: FlowPreviewer().also { instance = it }
            }
        }
    }

    fun test() {
//        singleFlow()
//        singleFlowOf()
//        singleChannelFlow()
        Klog.i("Begin ->")
        cloudFlowTest()
        Klog.i("End ->")
    }

    private suspend fun flowOnTest() {
        val flow = simpleFlow()
        flow.flowOn(Dispatchers.Default)
            .collect {
                logCurrentThread()
                Klog.i("next -> $it")
            }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun cloudFlowTest() {
        /// 开启一个协程 【非阻塞】
        GlobalScope.launch(ioDispatcher) {
            /// 🥶【冷流】
            /// 代码直到流被收集的时候才运行
            /// 先执行：first
            /// print: 1,2,3,4,5
            /// 再执行 anther
            /// print: 1,2,3,4,5

            Klog.i("----------------------------")
            Klog.i("[First]simple collect")
            val flow = simpleFlow()
            /// 超时
            withTimeoutOrNull(5000) {
                flow
                    .flowOn(Dispatchers.Default)
                    .collect {
                        Klog.i("[First] collect on ${Thread.currentThread().name} with value = $it")
                    }
            }
            Klog.i("[First] finished!")

            Klog.i("----------------------------")
            Klog.i("[Anther] simple collect")
            flow
                .take(2) /// 前两个
//                .takeWhile { it <= 3 }
                .flowOn(Dispatchers.Main)
                .collect {
                    Klog.i("[Anther] collect on ${Thread.currentThread().name} with value = $it")
                }
            Klog.i("[Anther] finished!")

            Klog.i("----------------------------")
            Klog.i("[Conflate] begin")
            flow
                /// 最新到来的 element 替换掉已有的
                .conflate()
                .collect {
                    delay(5000)
                    Klog.i("[Conflate] collect on ${Thread.currentThread().name} with value = $it")
                }
            Klog.i("[Conflate] finished!")
        }
    }

    private suspend fun simple(): Sequence<Int> = sequence { // 序列构建器
        for (i in 1..3) {
            Thread.sleep(1000) // 假装我们正在计算
            // TODO: yield 怎么理解
            yield(i) // 产生下一个值
        }
    }

    private suspend fun singleChannelFlow() {
        channelFlow<Int> {
            for (i in 1..5) {
                delay(5 * 1000)
                send(i)
            }
        }
            .flowOn(Dispatchers.IO)
            .collect {
                Klog.i("singleChannelFlow => $it")
            }
    }

    private suspend fun singleFlowOf() {
        val list: List<Int> = listOf(1, 2, 3, 4, 5)
        /// 会阻塞线程
        flowOf(list)
            .onEach { delay(10 * 1000) }
            .map { "$it" }
            .collect {
                Klog.i("singleFlowOf => $it")
            }
    }

    private suspend fun singleFlow() {
        flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }
            .flowOn(Dispatchers.IO)
            .collect {
                Klog.i("singleFlow => $it")
            }
    }

    private suspend fun performRequest(request: Int): String {
        delay(5000) // 模仿长时间运行的异步任务
        return "response $request"
    }

    private suspend fun simpleFlow(): Flow<Int> {
        return flow<Int> {
            for (i in 1..5) {
                delay(1000)
                Klog.i("emit on ${Thread.currentThread().name} value = $i")
                emit(i)
            }
        }
    }
}

