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