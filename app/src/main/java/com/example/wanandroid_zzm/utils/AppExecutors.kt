package com.example.wanandroid_zzm.utils

import android.os.Handler
import android.os.Looper
import com.sum.framework.log.LogUtil
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.math.max

/**
 * 线程池组件
 */
//声明了一个 Kotlin 单例对象 AppExecutors，包含了应用程序中常用的线程池配置。
object AppExecutors {

    //创建了一个 ThreadFactory 实例 threadFactory，用于自定义线程创建的行为。
    private val threadFactory = ThreadFactory {
        //使用 ThreadFactory 创建线程，it 表示传入的 Runnable 对象。
        Thread(it).apply {
            //设置线程优先级为后台优先级。
            priority = android.os.Process.THREAD_PRIORITY_BACKGROUND
            //设置线程未捕获异常的处理逻辑。
            setUncaughtExceptionHandler { t, e ->
                LogUtil.e(
                    "Thread<${t.name}> has uncaughtException",
                    e
                )
            }
        }
    }

    //创建一个名为 cpuIO 的 Executor 对象，用于 CPU 密集型的任务。
    val cpuIO: Executor =
        CpuIOThreadExecutor(threadFactory)
    //创建一个 DiskIOThreadExecutor 实例，用于处理磁盘 IO 操作的线程池。
    val diskIO: Executor =
        DiskIOThreadExecutor(threadFactory)
    //创建一个名为 mainThread 的 MainThreadExecutor 实例，用于在主线程执行任务。
    val mainThread = MainThreadExecutor()

    //创建一个内部类 MainThreadExecutor，实现了 Executor 接口，用于在主线程执行任务。
    class MainThreadExecutor : Executor {

        //创建一个主线程处理器 mainThreadHandler，用于在主线程执行任务。
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        //使用主线程处理器 mainThreadHandler 执行提交的任务。
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }

        //使用主线程处理器 mainThreadHandler 延迟执行提交的任务。
        fun executeDelay(command: Runnable, delayMillis: Long) {
            mainThreadHandler.postDelayed(command, delayMillis)
        }
    }

    //创建了一个内部类 DiskIOThreadExecutor，实现了 Executor 接口，用于处理磁盘 IO 操作的线程池。
    private class DiskIOThreadExecutor(threadFactory: ThreadFactory) : Executor {

        private val diskIO = Executors.newSingleThreadExecutor(threadFactory)

        override fun execute(command: Runnable) {
            val className = Throwable().stackTrace[1]?.className ?: "Undefined"
            val methodName = Throwable().stackTrace[1]?.methodName ?: "Undefined"
            diskIO.execute(RunnableWrapper("$className#$methodName", command))
        }
    }

    //创建了一个内部类 CpuIOThreadExecutor，实现了 Executor 接口，用于处理 CPU 密集型任务的线程池。
    private class CpuIOThreadExecutor(threadFactory: ThreadFactory) : Executor {

        private val cpuIO = ThreadPoolExecutor(
            2,
            max(2, Runtime.getRuntime().availableProcessors()),
            30,
            TimeUnit.SECONDS,
            ArrayBlockingQueue<Runnable>(128),
            threadFactory,
            object : ThreadPoolExecutor.DiscardOldestPolicy() {
                override fun rejectedExecution(r: Runnable?, e: ThreadPoolExecutor?) {
                    super.rejectedExecution(r, e)
                    LogUtil.e("CpuIOThreadExecutor#rejectedExecution => Runnable <$r>")
                }
            }
        )

        override fun execute(command: Runnable) {
            val name = Throwable().stackTrace[1].className
            cpuIO.execute(RunnableWrapper(name, command))
        }
    }

}

//创建了一个内部类 RunnableWrapper，实现了 Runnable 接口，用于包装提交的任务并运行。
private class RunnableWrapper(private val name: String, private val runnable: Runnable) : Runnable {
    override fun run() {
        Thread.currentThread().name = name
        runnable.run()
    }
}

