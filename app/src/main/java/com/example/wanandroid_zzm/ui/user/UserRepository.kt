package com.example.wanandroid_zzm.ui.user

import com.example.wanandroid_zzm.bean.ArticleList
import com.example.wanandroid_zzm.bean.UserInfoBean
import com.example.wanandroid_zzm.network.BaseRepository
import com.example.wanandroid_zzm.network.BaseResponse
import com.sum.network.manager.ApiManager

class UserRepository : BaseRepository() {


    suspend fun getUserInfo(): UserInfoBean? {
        return requestResponse {
            ApiManager.api.getUserInfo()
        }
    }

    suspend fun getCollectList(page:Int) : ArticleList?{
        return requestResponse {
            ApiManager.api.getCollectList(page)
        }
    }

}