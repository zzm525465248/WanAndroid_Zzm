package com.example.wanandroid_zzm.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.databinding.DataBindingUtil.getBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.wanandroid_zzm.MainActivity
import com.example.wanandroid_zzm.R
import com.example.wanandroid_zzm.bean.ArticleList
import com.example.wanandroid_zzm.bean.BannerBean
import com.example.wanandroid_zzm.databinding.FragmentHomeBinding
import com.example.wanandroid_zzm.databinding.RvArticlelistBinding
import com.example.wanandroid_zzm.ext.onClick
import com.example.wanandroid_zzm.ui.article.ArticleDetailsActivity
import com.example.wanandroid_zzm.utils.LaunchTimer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.sum.framework.base.BaseMvvmFragment
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener

class HomeFragment : BaseMvvmFragment<FragmentHomeBinding,HomeViewModel>()
     {
    private var page=1;
    private var cid=294;

    override fun initView(view: View, savedInstanceState: Bundle?) {
            getUiData()
            initClick()
    }

    override fun initData() {
        super.initData()
        mBinding!!.rvArtList.linear().divider {
            setDivider(10,true)
        }
        //从fm中调用
        mViewModel.ArticleListFlow(1,294)
        //项目列表
        mViewModel.articleListLiveData.observe(this){data ->
            mBinding!!.rvArtList.setup {
                var isRecord = false
                addType<ArticleList.ArticleListData>(R.layout.rv_articlelist)
                onBind {
                    //在第一个数据渲染完成时进行埋点操作
                    if(modelPosition==0&&!isRecord){
                        isRecord = true
                    }

                    val binding = getBinding<RvArticlelistBinding>() // ViewBinding/DataBinding都支持
                    Glide.with(this@HomeFragment).load(getModel<ArticleList.ArticleListData>().envelopePic).into(binding.imageView6)

                    //val data = holder.getModel<ArticleList.ArticleListData>()
                }
                R.id.rv_item.onClick {holder->
                    val intent :Intent = Intent(this.context,ArticleDetailsActivity::class.java)
                    val bundle :Bundle=Bundle()
                    bundle.putString("url", data?.get(modelPosition)?.link)
                    bundle.putBoolean("collect", data?.get(modelPosition)?.collect!!)
                    bundle.putInt("id", data[modelPosition].id!!)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }.models=data
        }
        //tab分类
        mViewModel.getClassArticle().observe(this){

            it?.forEachIndexed { index, item ->
                if (index<5){

                    val tabs=mBinding!!.tbClass.newTab()
                    tabs.text=item.name
                    tabs.id= item.id!!
                    mBinding!!.tbClass.addTab(tabs)
                }

            }
        }

        //Banner
        mViewModel.getBannerList().observe(this){
            Log.d("HomeFragment", it?.get(0)?.url.toString())
            activity?.reportFullyDrawn()

            mBinding?.banner?.viewTreeObserver?.addOnPreDrawListener(object :ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    LaunchTimer.stopRecord("首帧绘制时间：")
                    // 移除监听
                    mBinding?.banner?.viewTreeObserver?.removeOnPreDrawListener(this)
                    return false
                }

            })
            mBinding?.banner?.setAdapter(object : BannerImageAdapter<BannerBean>(it) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerBean?,
                    position: Int,
                    size: Int
                ) {
                    Glide.with(holder?.imageView!!)
                        .load(data?.imagePath)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                        .into(holder.imageView);
                }
            })?.addBannerLifecycleObserver(this)?.setBannerRound2(20.0F)?.setOnBannerListener { data, position ->
                Log.d(TAG, "$data")
                Log.d("z","$position")
            }
                ?.indicator=CircleIndicator(this.context)


        }
    }

    private fun initClick(){
        mBinding!!.tbClass.addOnTabSelectedListener(object :OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                cid=tab?.id!!
                mViewModel.getArticleList(page,cid)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        mBinding?.include1?.imageView4?.onClick {
            Log.i("click","dddd")
        }


    }

    private fun getUiData(){
       // mViewModel.getArticleList(page, cid)
        mViewModel.getClassArticle()
    }




}