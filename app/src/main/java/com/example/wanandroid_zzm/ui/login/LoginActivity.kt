package com.example.wanandroid_zzm.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import com.example.wanandroid_zzm.MainActivity
import com.example.wanandroid_zzm.R
import com.example.wanandroid_zzm.databinding.ActivityLoginBinding
import com.example.wanandroid_zzm.ext.click
import com.example.wanandroid_zzm.ext.invisible
import com.example.wanandroid_zzm.ext.isVisible
import com.example.wanandroid_zzm.ext.visible
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.StatusBarSettingHelper

class LoginActivity : BaseMvvmActivity<ActivityLoginBinding, LoginViewModel>() {
    private  var isShow=false
    override fun initView(savedInstanceState: Bundle?) {

       initClick()
        edtextChanged()
        StatusBarSettingHelper.setStatusBarTranslucent(this)
        StatusBarSettingHelper.statusBarLightMode(this, true)
    }

    override fun initData() {
        mViewModel.loginLiveData.observe(this) {  user ->
            //登录成功
            dismissLoading()
            user?.let {
                Log.d("loginActivity",user.token.toString())

//                UserServiceProvider.saveUserInfo(user)
//                UserServiceProvider.saveUserPhone(user.username)
                TipsToast.showTips("登录成功")
  //              MainServiceProvider.toMain(context = this)
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            } ?: kotlin.run {
                Log.d("loginActivity",user?.nickname.toString())
            }
        }
    }
    private fun initClick(){

        mBinding.bottom.click {
            toLogin()
        }
        mBinding.imgPdDisplay.click {
            pdDisplay()
        }
    }
    private fun pdDisplay(){
        isShow=!isShow
       if(isShow){
           mBinding.imgPdDisplay.setImageResource(R.drawable.kj)
           mBinding.editTextTextPassword.transformationMethod=HideReturnsTransformationMethod.getInstance()
       }else{
           mBinding.imgPdDisplay.setImageResource(R.drawable.bkj)
           mBinding.editTextTextPassword.transformationMethod=PasswordTransformationMethod.getInstance()
       }
    }
    //监听文本变化
    private fun edtextChanged(){
        mBinding.editTextTextPassword.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0?.length!=0){
                    mBinding.imgPdDisplay.visible()
                }else if(p0.isEmpty()){
                    mBinding.imgPdDisplay.invisible()
                }


                Log.d("edit",p0?.length.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    //登录
    private fun toLogin(){
        val userName =mBinding.editTextTextPersonName.text
        val passWord=mBinding.editTextTextPassword.text
        Log.d("loginActivity","登录")
        if(userName.isNullOrEmpty() || passWord.isNullOrEmpty()){
                TipsToast.showTips("账户或密码不能为空！")
                 return
        }
        mViewModel.login("$userName", "$passWord")
    }

}