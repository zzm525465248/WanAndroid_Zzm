package com.example.wanandroid_zzm.ui.home

import com.example.wanandroid_zzm.bean.ArticleList
import com.example.wanandroid_zzm.bean.BannerBean
import com.example.wanandroid_zzm.bean.ClassArticle
import com.example.wanandroid_zzm.network.BaseRepository
import com.sum.network.manager.ApiManager

class HomeRepository :BaseRepository() {
       suspend fun getArticleList(page:Int,cid:Int) :ArticleList?{
           return requestResponse {
               ApiManager.api.getArticleList(page,cid)
           }
       }

    suspend fun getClassArticle():MutableList<ClassArticle>?{
        return requestResponse {
            ApiManager.api.getClassArticle()
        }
    }

    suspend fun getBannerList():MutableList<BannerBean>?{
        return requestResponse {
            ApiManager.api.getBanner()
        }
    }
}