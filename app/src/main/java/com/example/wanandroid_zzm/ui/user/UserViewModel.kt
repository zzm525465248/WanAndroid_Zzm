package com.example.wanandroid_zzm.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.wanandroid_zzm.bean.ArticleList
import com.example.wanandroid_zzm.bean.UserInfoBean
import com.example.wanandroid_zzm.network.BaseResponse
import com.sum.framework.toast.TipsToast
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

class UserViewModel : BaseViewModel() {

    val userRepository by lazy { UserRepository() }
    val userInfoLiveData =MutableLiveData<UserInfoBean?>()
    val collectListLiveData: MutableLiveData<MutableList<ArticleList.ArticleListData>?> = MutableLiveData()

    fun getUserInfo():LiveData<UserInfoBean?>{
       launchUI(errorBlock={code, error ->
           TipsToast.showTips(error)

           userInfoLiveData.value=null
       }){
            val data=userRepository.getUserInfo()

            userInfoLiveData.value=data
       }
        return  userInfoLiveData
    }

    fun getCollectList(page:Int):LiveData<MutableList<ArticleList.ArticleListData>?>{
        launchUIWithResult(responseBlock = {
            ApiManager.api.getCollectList(page)
        },errorCall = object : IApiErrorCallback {
            override fun onError(code: Int?, error: String?) {
                super.onError(code, error)
                collectListLiveData.value=null
            }
        }, successBlock = {
            if(it==null|| it.datas.isNullOrEmpty() ){
                collectListLiveData.value=null
            }else{
                collectListLiveData.value=it.datas
            }
        })
        return  collectListLiveData
    }
}