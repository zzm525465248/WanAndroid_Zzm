package com.example.wanandroid_zzm.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.wanandroid_zzm.bean.UserInfoBean
import com.example.wanandroid_zzm.network.BaseResponse
import com.sum.framework.toast.TipsToast
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

class UserViewModel : BaseViewModel() {


    fun getUserInfo():LiveData<BaseResponse<UserInfoBean>?>{
        return liveData {
            val response=safeApiCall(errorBlock = {code,message->
                Log.d("T",message.toString())
                TipsToast.showTips(message)
            }){
                ApiManager.api.getUserInfo()
            }
            emit(response)
        }

    }
}