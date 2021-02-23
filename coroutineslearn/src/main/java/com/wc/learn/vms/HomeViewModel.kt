package com.wc.learn.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wc.learn.base.BaseViewModel
import com.wc.learn.data.ArticleData
import com.wc.learn.data.BannerData
import com.wc.learn.data.catchException
import com.wc.learn.data.next
import com.wc.learn.network.getNetworkService
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    val articleData: MutableLiveData<MutableList<ArticleData>> = MutableLiveData()

    val bannerData: MutableLiveData<List<BannerData>> = MutableLiveData()

    val refreshType: MutableLiveData<Int> = MutableLiveData()

    init {
        println("home view model init")
        getData(0)
        getBanner()
    }

    fun getData(page: Int) {
        viewModelScope.launch {
            refreshType.postValue(page)
            val tops = if (page == 0) getRemoteData(getNetworkService().getHomeTopArticles()) else null
            tops?.forEach { articleData: ArticleData ->
                articleData.isTop = true
            }
            println("tops = ${tops.toString()}")
            val data = getRemoteData(getNetworkService().getHomeArticles(page))?.datas
            tops?.let {
                data?.addAll(0, it)
            }
            println("datas = ${data.toString()}")
            articleData.postValue(data)
        }
    }

    fun getBanner() {
        viewModelScope.launch {
//            request(false) {
//                getBanners()
//            }.next {
//                bannerData.value = getRemoteData(this)
//            }.catchException {
//
//            }
            val banners = getRemoteData(getNetworkService().getBanners())
            bannerData.postValue(banners)
        }
    }
}