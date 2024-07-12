package com.example.wanandroid_zzm.ui.login

import com.example.wanandroid_zzm.bean.User
import com.example.wanandroid_zzm.network.BaseRepository
import com.sum.network.manager.ApiManager

class LoginRepository :BaseRepository() {

    suspend fun  login(username: String, password: String) :User?{
        return requestResponse {
            ApiManager.api.login(username, password)
        }
    }
}