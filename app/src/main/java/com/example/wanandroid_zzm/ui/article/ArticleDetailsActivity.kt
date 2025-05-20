package com.example.wanandroid_zzm.ui.article

import android.content.Intent
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
import com.example.wanandroid_zzm.ext.onClick
import com.example.wanandroid_zzm.ui.login.LoginActivity
import com.sum.framework.base.BaseDataBindActivity
import com.sum.framework.base.BaseMvvmActivity
import com.sum.framework.toast.TipsToast
import com.sum.framework.utils.StatusBarSettingHelper
import com.sum.network.error.ERROR

class ArticleDetailsActivity : BaseMvvmActivity<ActivityArticleDetailsBinding,ArticleDetailsViewModel>() {
    var isCollect :Boolean =false;
    var bundle :Bundle?=null
    override fun initView(savedInstanceState: Bundle?) {
        initWeb()
        initTitleClick()
        supportActionBar!!.hide()
        StatusBarSettingHelper.setStatusBarTranslucent(this)
        StatusBarSettingHelper.statusBarLightMode(this, true)

    }
    private fun initWeb(){
        bundle=intent.extras
        val url=bundle?.getString("url")
        isCollect= bundle?.getBoolean("collect")!!
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
        mBinding.include.imageView7.onClick {
            finish();
        }
        mBinding.include.imageView8.setOnClickListener {
            val popupMenu:PopupMenu = PopupMenu(this@ArticleDetailsActivity,it)
            popupMenu.menuInflater.inflate(R.menu.title_popup_menu, popupMenu.menu)
            popupMenu.show()
            Log.d("ArticleDetailsActivity",isCollect.toString())
            if(isCollect){
                popupMenu.menu.getItem(1).title="取消收藏"
            }else{
                popupMenu.menu.getItem(1).title="收藏"
            }
          //  popupMenu.getMenu().findItem(checkedItemId).setChecked(true);


            popupMenu.setOnMenuItemClickListener(object :PopupMenu.OnMenuItemClickListener{
                override fun onMenuItemClick(p0: MenuItem?): Boolean {

                    when (p0?.itemId){
                        //分享
                        R.id.item1 -> {
                            Log.d("popupMenu.onMenuItemClick","dz")
                        }
                        R.id.item2 ->{
                            setCollectView(bundle!!.getInt("id"))
                        }
                    }
                    return true
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webview.destroy()
    }

    /**
     * 收藏和取消收藏
     * @param position
     */
    private fun setCollectView(id: Int) {



            val collect = isCollect ?: false
            mViewModel.collectArticle(id, collect).observe(this) {
                dismissLoading()
                it?.let {
                    val tipsRes =
                        if (collect) "取消收藏" else "收藏成功"
                    TipsToast.showSuccessTips(tipsRes)
                    Log.d("Collect",it.toString())
                    isCollect=!isCollect
                }

                if (it == ERROR.UNLOGIN.code){
                    val intent : Intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }

}