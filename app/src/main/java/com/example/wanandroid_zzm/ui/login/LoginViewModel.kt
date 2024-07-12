package com.example.wanandroid_zzm.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wanandroid_zzm.bean.User
import com.sum.framework.toast.TipsToast
import com.sum.network.viewmodel.BaseViewModel

class LoginViewModel : BaseViewModel() {
    val loginLiveData = MutableLiveData<User?>()
    val loginRepository by lazy { LoginRepository() }
    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     * @return
     */
    fun login(username: String, password: String): LiveData<User?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            loginLiveData.value = null
        }) {
            val data = loginRepository.login(username, password)
            loginLiveData.value = data
        }
        return loginLiveData
    }
}