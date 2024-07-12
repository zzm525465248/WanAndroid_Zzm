package com.sum.network.manager

import com.example.wanandroid_zzm.network.Api


/**
 * @author mingyan.su
 * @date   2023/2/27 21:14
 * @desc   API管理器
 */
object ApiManager {
    val api by lazy { HttpManager.create(Api::class.java) }
}