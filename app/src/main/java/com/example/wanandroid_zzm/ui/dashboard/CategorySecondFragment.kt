package com.example.wanandroid_zzm.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout.HORIZONTAL
import android.widget.GridLayout.VERTICAL
import androidx.appcompat.widget.ViewUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.wanandroid_zzm.R
import com.example.wanandroid_zzm.bean.NaviBean
import com.example.wanandroid_zzm.databinding.FragmentCategorySecondBinding
import com.example.wanandroid_zzm.databinding.NaviItemRvBinding
import com.example.wanandroid_zzm.databinding.NaviItemRvBindingImpl
import com.example.wanandroid_zzm.databinding.RvNaviBinding
import com.example.wanandroid_zzm.ext.onClick
import com.example.wanandroid_zzm.ui.article.ArticleDetailsActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sum.framework.base.BaseMvvmFragment

class CategorySecondFragment : BaseMvvmFragment<FragmentCategorySecondBinding,NaviViewModel>() {

    companion object {
        fun newInstance(jsonStr: String): CategorySecondFragment {
            val fragment = CategorySecondFragment()
            val bundle = Bundle()
            bundle.putString("list", jsonStr)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mBinding!!.rv.grid(2,VERTICAL).divider {
            setDivider(4,true)
        }
        val listJson=arguments?.getString("list","")

        val gson = Gson()
        val list: List<NaviBean.Article> = gson.fromJson(listJson, object : TypeToken<List<NaviBean.Article>>() {}.type)
        mBinding!!.rv.setup {
            addType<NaviBean.Article>(R.layout.navi_item_rv)
            onBind {
                val binding = getBinding<NaviItemRvBinding>()
                com.example.wanandroid_zzm.utils.ViewUtils.setClipViewCornerRadius(binding.tvTitle,16)

                itemView.onClick {
                    val intent : Intent = Intent(this.context, ArticleDetailsActivity::class.java)
                    val bundle :Bundle=Bundle()
                    bundle.putString("url", getModel<NaviBean.Article>(modelPosition).link)

                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }

        }.models=list
        Log.d("CategorySecondFragment","${list[0].title}")

    }


}