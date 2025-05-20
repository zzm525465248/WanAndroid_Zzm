package com.example.wanandroid_zzm.ui.dashboard

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.wanandroid_zzm.R
import com.example.wanandroid_zzm.adapter.ViewPage2FragmentAdapter
import com.example.wanandroid_zzm.bean.NaviBean
import com.example.wanandroid_zzm.databinding.FragmentDashboardBinding
import com.example.wanandroid_zzm.databinding.RvArticlelistBinding
import com.example.wanandroid_zzm.databinding.RvNaviBinding
import com.example.wanandroid_zzm.ext.onClick
import com.example.wanandroid_zzm.ext.saveAs
import com.example.wanandroid_zzm.ext.visible
import com.example.wanandroid_zzm.utils.dpToPx
import com.example.wanandroid_zzm.utils.getColorFromResource
import com.google.gson.Gson
import com.mob.tools.utils.DH.SyncMtd.getModel
import com.sum.framework.base.BaseMvvmFragment

class NaviFragment : BaseMvvmFragment<FragmentDashboardBinding,NaviViewModel>(){
    private var mCurrentSelectPosition = 0
    private var mViewPagerAdapter: ViewPage2FragmentAdapter? = null
    private var fragments = SparseArray<Fragment>()
    private var rvAdapter : com.drake.brv.BindingAdapter? =null
    /**
     * 背景圆角
     */
    private val mRadius = dpToPx(8f)
    override fun initView(view: View, savedInstanceState: Bundle?) {
            initTabRecyclerView()
            initViewPager()
    }

    private fun initTabRecyclerView(){
        mBinding!!.rv.linear().divider {
           setDivider(4,true)
        }
    }

    private fun initViewPager(){
        activity?.let {
            mViewPagerAdapter = ViewPage2FragmentAdapter(childFragmentManager, lifecycle, fragments)
//            mViewPagerAdapter = ViewPage2FragmentAdapter(it, fragments)
        }
        mBinding?.viewpager?.apply {
            offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
            orientation = ViewPager2.ORIENTATION_VERTICAL
            registerOnPageChangeCallback(viewPager2Callback)
            adapter = mViewPagerAdapter
        }
    }

    override fun initData() {
        super.initData()
        showLoading()
        mViewModel.getCategoryData()
        mViewModel.categoryItemLiveData.observe(this){data->
            dismissLoading()

            data?.firstOrNull()?.isSelected=true
            data?.forEachIndexed{index,item->
                val fragment = CategorySecondFragment.newInstance(Gson().toJson(item.articles) ?: "")
                fragments.append(index, fragment)
            }
            data?.size?.let { mViewPagerAdapter?.notifyItemRangeChanged(0, it) }
            mBinding!!.rv.setup {

                addType<NaviBean>(R.layout.rv_navi)
                onBind {
                    Log.d("Loading category",data?.get(modelPosition)?.isSelected.toString()+data?.get(modelPosition)?.name)
                    rvAdapter=this@setup
                    val binding = getBinding<RvNaviBinding>() // ViewBinding/DataBinding都支持
                    if(this.getModel<NaviBean>().isSelected==true){
                        binding.viewTag.visible()
                        binding.background.background=getBgDrawable(R.color.white,mRadius,mRadius,mRadius,mRadius)
                        binding.tvTitle.typeface = Typeface.DEFAULT_BOLD
                    }else{
                     //   when(modelPosition){
                            binding.background.background =
                                getBgDrawable(R.color.color_f0f2f4,mRadius,mRadius,mRadius,mRadius)
                            //前一个Item背景
//                            modelPosition - 1 -> {
//
//                            }
//                            //后一个Item背景
//                            modelPosition + 1 -> {
//                                binding.background.background =
//                                    getBgDrawable(R.color.color_f0f2f4,mRadius,mRadius,mRadius,mRadius)
//                            }
//                            else -> {
//                                binding.background.background = getBgDrawable(R.color.color_f0f2f4,mRadius,mRadius,mRadius,mRadius)
//                            }
                  //      }
                      binding.tvTitle.typeface = Typeface.DEFAULT
                    }
                    itemView.onClick {
                        if(mCurrentSelectPosition!=modelPosition){
                            getModel<NaviBean>(mCurrentSelectPosition).isSelected=false
                            getModel<NaviBean>(modelPosition).isSelected=true
                            mCurrentSelectPosition=modelPosition
                            notifyDataSetChanged()
                            mBinding?.viewpager?.setCurrentItem(modelPosition,false)
                           // mBinding?.rv?.smoothScrollToPosition(modelPosition)
                        }
                    }

                }
            }.models=data
        }
    }


    /**
     * VIewPager2选择回调
     */
    private val viewPager2Callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if(mCurrentSelectPosition!=position){
                rvAdapter?.let {
                  it.getModel<NaviBean>(mCurrentSelectPosition).isSelected=false
                  it. getModel<NaviBean>(position).isSelected=true
                    it.notifyDataSetChanged()
                }
                mCurrentSelectPosition=position
               // notifyDataSetChanged()
                mBinding?.viewpager?.setCurrentItem(position,false)
                mBinding?.rv?.smoothScrollToPosition(position)
            }

        }
    }

    override fun onDestroyView() {
        mBinding?.viewpager?.unregisterOnPageChangeCallback(viewPager2Callback)
        super.onDestroyView()
    }


    /**
     * 设置背景
     * @param color 背景颜色
     * @param leftTopRadius top-left
     * @param rightTopRadius top-right
     * @param leftBottomRadius bottom-right
     * @param rightBottomRadius bottom-left
     */
    private fun getBgDrawable(
        @ColorRes color: Int,
        leftTopRadius: Float = 0.0f,
        rightTopRadius: Float = 0.0f,
        rightBottomRadius: Float = 0.0f,
        leftBottomRadius: Float = 0.0f
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(getColorFromResource(color))
            shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(
                leftTopRadius,
                leftTopRadius,
                rightTopRadius,
                rightTopRadius,
                rightBottomRadius,
                rightBottomRadius,
                leftBottomRadius,
                leftBottomRadius
            )
        }
    }
}