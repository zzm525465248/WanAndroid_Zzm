package com.example.wanandroid_zzm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.wanandroid_zzm.utils.AppExecutors
import com.example.wanandroid_zzm.utils.LaunchTimer
import com.sum.framework.toast.TipsToast
import com.sum.stater.dispatcher.TaskDispatcher
import com.sum.tea.task.InitAppManagerTask
import com.sum.tea.task.InitBRvTask
import com.sum.tea.task.InitMmkvTask
import com.sum.tea.task.InitSumHelperTask


/**

 */
class Application : Application() {
    // 应用最早回调的生命周期方法
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
         LaunchTimer.startRecord()
//        SPUtils.getInstance("SumTea")
       AppExecutors.cpuIO.execute(Runnable {
             MultiDex.install(base)
        })
    }

    // 复写返回this
//    override fun getApplicationContext(): Context {
//        return this
//    }

    override fun onCreate() {
        super.onCreate()
//        SumAppHelper.init(this,false)
//        MMKV.initialize(this)

        //BRV初始化
//          BRV.modelId=BR.m
//        //注册APP前后台切换监听
//        appFrontBackRegister()
//        // App启动立即注册监听
//        registerActivityLifecycle()
          TipsToast.init(this)
//
        //1.启动器：TaskDispatcher初始化
        TaskDispatcher.init(this)
        //2.创建dispatcher实例
        val dispatcher: TaskDispatcher = TaskDispatcher.createInstance()

        //3.添加任务并且启动任务
        dispatcher.addTask(InitSumHelperTask(this))
                .addTask(InitMmkvTask())
                .addTask(InitAppManagerTask())
                .addTask(InitBRvTask())
                .start()

        //4.等待，需要等待的方法执行完才可以往下执行
        dispatcher.await()

        // TraceView
//        Debug.startMethodTracing() // 开始记录
//        initSumHelper()
//        Debug.stopMethodTracing() // 结束记录

        // SysTrace
//        Trace.beginSection("initMmkv") // 添加标识，方便查询
//        initMmkv()
//        Trace.endSection()
    }

    /**
     * 注册APP前后台切换监听
     */
//    private fun appFrontBackRegister() {
//        AppFrontBack.register(this, object : AppFrontBackListener {
//            override fun onBack(activity: Activity?) {
//                LogUtil.d("onBack")
//            }
//
//            override fun onFront(activity: Activity?) {
//                LogUtil.d("onFront")
//            }
//        })
//    }

    /**
     * 注册Activity生命周期监听
     */
//    private fun registerActivityLifecycle() {
//        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
//            override fun onActivityPaused(activity: Activity) {
//            }
//
//            override fun onActivityStarted(activity: Activity) {
//            }
//
//            override fun onActivityDestroyed(activity: Activity) {
//                ActivityManager.pop(activity)
//            }
//
//            override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
//            }
//
//            override fun onActivityStopped(activity: Activity) {
//            }
//
//            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
//
//            }
//
//            override fun onActivityResumed(activity: Activity) {
//            }
//        })
//    }
}