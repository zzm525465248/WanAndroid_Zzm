package com.example.wanandroid_zzm.bean

data class NaviBean(
    var isSelected: Boolean?,
    val articles: List<Article?>?,
    val cid: Int?,
    val name: String?
) {
    data class Article(
        val adminAdd: Boolean?,
        val apkLink: String?,
        val audit: Int?,
        val author: String?,
        val canEdit: Boolean?,
        val chapterId: Int?,
        val chapterName: String?,
        val collect: Boolean?,
        val courseId: Int?,

        val envelopePic: String?,
        val fresh: Boolean?,

        val id: Int?,
        val isAdminAdd: Boolean?,
        val link: String?,
        val niceDate: String?,
        val niceShareDate: String?,



        val publishTime: Long?,
        val realSuperChapterId: Int?,
        val selfVisible: Int?,
        val shareDate: Long?,
        val shareUser: String?,
        val superChapterId: Int?,
        val superChapterName: String?,
        val tags: List<Any?>?,
        val title: String?,
        val type: Int?,
        val userId: Int?,
        val visible: Int?,
        val zan: Int?
    )
}