package com.example.wanandroid_zzm.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_zzm.bean.NaviBean
import com.sum.framework.toast.TipsToast
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

class NaviViewModel : BaseViewModel() {
    val categoryItemLiveData = MutableLiveData<MutableList<NaviBean>?>()

    /**
     * 获取分类信息
     * 不依赖repository,错误回调实现IApiErrorCallback
     */
    fun getCategoryData() {
        launchUIWithResult(responseBlock = {
            ApiManager.api.getNaviList()
        }, errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                super.onError(code, error)
                TipsToast.showTips(error)
                categoryItemLiveData.value = null
            }
        }) {
            categoryItemLiveData.value = it
        }
    }

}