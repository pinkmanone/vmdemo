package com.wc.learn.network

import android.util.Log
import com.wc.learn.data.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

 val service: Network by lazy {
    val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                        override fun log(message: String) {
                            Log.i("net", message)
                        }
                    }).apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
            )
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    retrofit.create(Network::class.java)
}

fun getNetworkService() = service


interface Network {


    /**
     * 获取首页文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): NetData<PageData<ArticleData>>

    /**
     * 获取首页置顶文章
     */
    @GET("article/top/json")
    suspend fun getHomeTopArticles(): NetData<MutableList<ArticleData>>

    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun getCommonSites(): NetData<List<CommonWebsite>>

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    suspend fun getHotKeys(): NetData<List<HotKeywords>>

    /**
     * 知识体系
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): NetData<List<KnowledgeTag>>

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun getBanners(): NetData<List<BannerData>>

    @GET("wenda/list/{page}/json")
    suspend fun getQA(@Path("page") page: Int): NetData<PageData<ArticleData>>

}