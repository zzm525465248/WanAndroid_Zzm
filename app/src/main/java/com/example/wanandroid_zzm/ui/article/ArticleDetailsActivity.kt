package com.example.wanandroid_zzm.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import com.example.wanandroid_zzm.R
import com.example.wanandroid_zzm.databinding.ActivityArticleDetailsBinding
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.utils.StatusBarSettingHelper

class ArticleDetailsActivity : BaseMvvmActivity<ActivityArticleDetailsBinding,ArticleDetailsViewModel>() {
    override fun initView(savedInstanceState: Bundle?) {
        initWeb()
        initTitleClick()
        supportActionBar!!.hide()
        StatusBarSettingHelper.setStatusBarTranslucent(this)
        StatusBarSettingHelper.statusBarLightMode(this, true)

    }
    private fun initWeb(){
        val bundle=intent.extras
        val url=bundle?.getString("url")
        val webSettings:WebSettings =  mBinding.webview.settings
        webSettings.apply {
            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
            javaScriptEnabled=true
            //将图片调整到适合webview的大小
            useWideViewPort=true
            // 缩放至屏幕的大小
            loadWithOverviewMode=true
            //支持缩放，默认为true。是下面那个的前提。
            setSupportZoom(true)
            //设置内置的缩放控件。若为false，则该WebView不可缩放
            builtInZoomControls=true
            //隐藏原生的缩放控件
            displayZoomControls=false
            //设置可以访问文件
            allowFileAccess=true
            //支持通过JS打开新窗口
            javaScriptCanOpenWindowsAutomatically=true
            //支持自动加载图片
            loadsImagesAutomatically=true
            //设置编码格式
            defaultTextEncodingName="utf-8"
        }
        mBinding.webview.loadUrl(url.toString())
    }

    private fun initTitleClick(){
        mBinding.include.imageView8.setOnClickListener {
            val popupMenu:PopupMenu = PopupMenu(this@ArticleDetailsActivity,it)
            popupMenu.menuInflater.inflate(R.menu.title_popup_menu, popupMenu.menu)
            popupMenu.show()
          //  popupMenu.getMenu().findItem(checkedItemId).setChecked(true);
            popupMenu.setOnMenuItemClickListener(object :PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(p0: MenuItem?): Boolean {
                    when (p0?.itemId){
                        //分享
                        R.id.item1 -> {
                            Log.d("popupMenu.onMenuItemClick","dz")
                        }
                        R.id.item2 ->{

                        }
                    }
                    return true
                }

            })
        }
    }
}