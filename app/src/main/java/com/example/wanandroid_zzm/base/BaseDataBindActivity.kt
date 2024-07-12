package com.sum.framework.base

import android.view.LayoutInflater

import androidx.viewbinding.ViewBinding
import com.example.wanandroid_zzm.base.BaseActivity
import com.example.wanandroid_zzm.ext.saveAs
import com.example.wanandroid_zzm.ext.saveAsUnChecked

import java.lang.reflect.ParameterizedType

/**
 * @author mingyan.su
 * @date   2023/2/26 11:48
 * @desc   dataBinding Activity基类
 */
abstract class BaseDataBindActivity<DB : ViewBinding> : BaseActivity() {
    lateinit var mBinding: DB

    override fun setContentLayout() {
//      mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        //使用反射机制动态加载布局
        val type = javaClass.genericSuperclass
        val vbClass: Class<DB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setContentView(mBinding.root)
    }

    override fun getLayoutResId(): Int = 0
}