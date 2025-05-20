package com.example.wanandroid_zzm.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.wanandroid_zzm.bean.ArticleList
import com.example.wanandroid_zzm.bean.BannerBean
import com.example.wanandroid_zzm.bean.ClassArticle
import com.sum.framework.toast.TipsToast
import com.sum.network.callback.IApiErrorCallback
import com.sum.network.manager.ApiManager
import com.sum.network.viewmodel.BaseViewModel

class HomeViewModel : BaseViewModel() {
    val articleListLiveData: MutableLiveData<MutableList<ArticleList.ArticleListData>?> = MutableLiveData()
    val classArticleLiveData :MutableLiveData<MutableList<ClassArticle>?> = MutableLiveData()
    val homeRepository by lazy { HomeRepository() }

    fun getArticleList(page: Int, cId: Int) :LiveData<MutableList<ArticleList.ArticleListData>?>{
       launchUIWithResult(responseBlock = {
          ApiManager.api.getArticleList(page,cId)
       },errorCall = object : IApiErrorCallback{
           override fun onError(code: Int?, error: String?) {
               super.onError(code, error)
               articleListLiveData.value=null
           }
       }, successBlock = {
           if(it==null|| it.datas.isNullOrEmpty() ){
                articleListLiveData.value=null
           }else{
               articleListLiveData.value=it.datas
           }
       })
        return  articleListLiveData
    }
    fun ArticleListFlow(page: Int, cId: Int){
        launchFlow(errorCall = object : IApiErrorCallback{
            override fun onError(code: Int?, error: String?) {
                super.onError(code, error)
            }

            override fun onLoginFail(code: Int?, error: String?) {
                super.onLoginFail(code, error)
            }
        }, requestCall = {ApiManager.api.getArticleList(page,cId)},null, successBlock = {
            //数据获取成功
            Log.d("flow_test",it?.size.toString())
        })
    }

    //获取tab分类
    fun getClassArticle() : LiveData<MutableList<ClassArticle>?>{
        return  liveData {
             val  response =safeApiCall(errorBlock = { code,errorMessage ->
                 TipsToast.showTips(errorMessage)

             }){
                    homeRepository.getClassArticle()
             }
              emit(response)
        }
    }
    //获取banner
    fun getBannerList():LiveData<MutableList<BannerBean>?>{
        return  liveData {
            val response=safeApiCall(errorBlock = {code,errorMessage ->
                TipsToast.showTips(errorMessage)
            }){
                homeRepository.getBannerList()
            }
            emit(response)
        }
    }

}