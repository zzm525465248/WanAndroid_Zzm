package com.example.wanandroid_zzm.bean

data class UserInfoBean(
    val coinInfo: CoinInfo?,
    val collectArticleInfo: CollectArticleInfo?,
    val userInfo: UserInfo?
) {
    data class CoinInfo(
        val coinCount: Int?,
        val level: Int?,
        val nickname: String?,
        val rank: String?,
        val userId: Int?,
        val username: String?
    )

    data class CollectArticleInfo(
        val count: Int?
    )

    data class UserInfo(
        val admin: Boolean?,
        val chapterTops: List<Any?>?,
        val coinCount: Int?,
        val collectIds: List<Int?>?,
        val email: String?,
        val icon: String?,
        val id: Int?,
        val nickname: String?,
        val password: String?,
        val publicName: String?,
        val token: String?,
        val type: Int?,
        val username: String?
    )
}