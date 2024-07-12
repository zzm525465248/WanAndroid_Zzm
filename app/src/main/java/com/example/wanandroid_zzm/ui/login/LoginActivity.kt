package com.example.wanandroid_zzm.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.wanandroid_zzm.databinding.ActivityLoginBinding
import com.example.wanandroid_zzm.ext.click
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.StatusBarSettingHelper

class LoginActivity : BaseMvvmActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.button.click {
            toLogin()
        }
        StatusBarSettingHelper.setStatusBarTranslucent(this)
        StatusBarSettingHelper.statusBarLightMode(this, true)
    }

    override fun initData() {
        mViewModel.loginLiveData.observe(this) { user ->
            //登录成功
            dismissLoading()
            user?.let {
                Log.d("loginActivity",user.nickname.toString())
//                UserServiceProvider.saveUserInfo(user)
//                UserServiceProvider.saveUserPhone(user.username)
              //  TipsToast.showTips(R.string.success_login)
//                MainServiceProvider.toMain(context = this)
               // finish()
            } ?: kotlin.run {

            }
        }
    }

    private fun toLogin(){
        Log.d("loginActivity","登录")
        mViewModel.login("zzm525465248qaq", "123456")
    }

}