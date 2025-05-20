package com.example.wanandroid_zzm.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.wanandroid_zzm.R
import com.example.wanandroid_zzm.bean.ArticleList
import com.example.wanandroid_zzm.databinding.FragmentNotificationsBinding
import com.example.wanandroid_zzm.databinding.RvArticlelistBinding
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
        mViewModel.getUserInfo().observe(this){data->
            Log.d("UserFrag",data?.userInfo?.nickname.toString())
            if(data==null){
                mBinding?.textView3?.text="请先登录"
            }else{
             //   mBinding!!.textView3.text= it.userInfo?.nickname
                mBinding.apply {

                }
                mBinding!!.let {
                    it.textView3.text= data.userInfo?.nickname
                    it.textView6.text= data.coinInfo?.level.toString()
                    it.textView8.text= data.coinInfo?.rank
                    it.textView10.text= data.collectArticleInfo?.count.toString()
                }
            }
        }
        mBinding!!.RvCollect.linear().divider {
            setDivider(10,true)
        }
        mViewModel.getCollectList(0).observe(this){

            Log.d("UserFrag",it?.get(0)?.title.toString())
            mBinding!!.RvCollect.setup {
               // Log.d("UserFrag",it)
                addType<ArticleList.ArticleListData>(R.layout.rv_articlelist)
                onBind {
                    val binding = getBinding<RvArticlelistBinding>() // ViewBinding/DataBinding都支持
                    if(getModel<ArticleList.ArticleListData>().envelopePic==""){
                        binding.imageView6.setImageResource(R.mipmap.default_img)
                    }else{
                        Glide.with(this@UserFragment).load(getModel<ArticleList.ArticleListData>().envelopePic).into(binding.imageView6)
                    }



                }
            }.models=it
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