package com.example.wanandroid_zzm.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wanandroid_zzm.R
import com.example.wanandroid_zzm.databinding.FragmentNotificationsBinding
import com.example.wanandroid_zzm.ext.click
import com.example.wanandroid_zzm.ext.setUrlCircle
import com.example.wanandroid_zzm.ui.login.LoginActivity
import com.sum.framework.base.BaseMvvmFragment

class UserFragment : BaseMvvmFragment<FragmentNotificationsBinding,UserViewModel>() {
    override fun initView(view: View, savedInstanceState: Bundle?) {
        mViewModel.getUserInfo()
        initClick()
    }

    override fun initData() {
        super.initData()
        mBinding?.imagUserface?.setUrlCircle(R.mipmap.angry_face_color.toString())
        mViewModel.getUserInfo().observe(this){
            Log.d("UserFrag",it?.data.toString())
            if(it?.errorCode==-1001){
                mBinding?.textView3?.text="请先登录"
            }else{
                mBinding!!.textView3.text=it?.data?.userInfo?.nickname
            }
        }
    }
    private fun initClick(){
        mBinding?.let {
            it.textView3.click {
                if(it.text=="请先登录"){
                    val intent:Intent=Intent(this.context,LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}