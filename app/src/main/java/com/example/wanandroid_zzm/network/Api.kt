package com.example.wanandroid_zzm.network

import androidx.annotation.Nullable
import com.example.wanandroid_zzm.bean.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): BaseResponse<User?>?

    /**
     * 项目二级列表
     * @param page  分页数量
     * @param cid    项目分类的id
     */
    @GET("/project/list/{page}/json?")
    suspend fun getArticleList(
        @Path("page") page:Int,
        @Query("cid") cid:Int,
    ):BaseResponse<ArticleList>

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    suspend fun getClassArticle():BaseResponse<MutableList<ClassArticle>>

    /*
    首页Banner
     */
    @GET("banner/json")
    suspend fun getBanner():BaseResponse<MutableList<BannerBean>>


    /**
     * 收藏
     */
    @FormUrlEncoded
    @POST("/lg/collect/{id}/json")
    suspend fun  postCollect(
        @Path("id") id:Int,
    ) :BaseResponse<Any>?

    /**
     * 获取个人消息
     */
    @GET("/user/lg/userinfo/json")
    suspend fun getUserInfo():BaseResponse<UserInfoBean>

    /**
     * 获取收藏列表
     */
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectList(
        @Path("page") page:Int
    ):BaseResponse<ArticleList>

    /**
     * 导航
     */
    @GET("/navi/json")
    suspend fun getNaviList():BaseResponse<MutableList<NaviBean>>



}