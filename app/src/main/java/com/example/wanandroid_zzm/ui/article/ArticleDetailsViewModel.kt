package com.example.wanandroid_zzm.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.wanandroid_zzm.network.BaseResponse
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

class ArticleDetailsViewModel : BaseViewModel() {

    fun postCollect(id:Int) :LiveData<BaseResponse<Any>?>{
        return liveData {
            val response=safeApiCall(errorBlock = {code,errorMessage ->

            }){
                ApiManager.api.postCollect(id)
            }
        }
    }
}