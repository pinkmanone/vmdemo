package com.wc.learn.data

import java.lang.Exception

//接口数据格式
class NetData<T>(val data: T? = null, val errorCode: Int? = null, val errorMsg: String? = null, var exception: Exception? = null) {}

inline fun <T> NetData<T>.next(block: NetData<T>.() -> Unit): NetData<T> {
    return if (exception == null) {//无异常，正确结果回调
        block.invoke(this)
        this
    } else {//有异常直接执行
        this
    }
}

inline fun <T> NetData<T>.catchException(block: Exception.() -> Unit) {
    if (exception != null) {
        block(exception!!)
    }
}

//分页数据格式
data class PageData<T>(val curPage: Int, val offset: Int, val over: Boolean, val pageCount: Int, val size: Int, val total: Int, val datas: MutableList<T>)

//文章实体
data class ArticleData(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val host: String,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int,
        var isTop: Boolean
)

//文章tag
data class Tag(val name: String, val url: String)

//常用网站
data class CommonWebsite(
        val category: String,
        val icon: String,
        val id: Int,
        val link: String,
        val name: String,
        val order: Int,
        val visible: Int
)

//搜索热词
data class HotKeywords(
        val id: Int,
        val link: String,
        val name: String,
        val order: Int,
        val visible: Int
)

//知识体系
data class KnowledgeTag(
        val children: List<KnowledgeTag>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
)

//banner
data class BannerData(
        val desc: String,
        val id: Int,
        val imagePath: String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
)