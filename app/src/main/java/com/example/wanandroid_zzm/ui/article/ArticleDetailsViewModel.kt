package com.example.wanandroid_zzm.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.wanandroid_zzm.network.BaseResponse
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.error.ERROR
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

class ArticleDetailsViewModel : BaseViewModel() {
    val collectLiveData: MutableLiveData<Int?> = MutableLiveData()

    fun collectArticle(id: Int, isCollect: Boolean): LiveData<Int?> {
        launchUIWithResult(responseBlock = {
            if (!isCollect) {
                //收藏站内文章
                ApiManager.api.postCollect(id)
            } else {
                //取消收藏站内文章
                ApiManager.api.postCollect(id)
            }
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?)  {
                super.onError(code, error)
                collectLiveData.value = null
            }

            override fun onLoginFail(code: Int?, error: String?) {
                super.onLoginFail(code, error)
                collectLiveData.value = ERROR.UNLOGIN.code
            }
        }) {
            collectLiveData.value = if (isCollect) 0 else 1
        }
        return collectLiveData
    }
}