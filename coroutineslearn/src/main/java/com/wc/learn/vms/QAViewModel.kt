package com.wc.learn.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wc.learn.base.BaseViewModel
import com.wc.learn.data.ArticleData
import com.wc.learn.data.next
import com.wc.learn.network.getNetworkService
import kotlinx.coroutines.launch

class QAViewModel : BaseViewModel() {
    val articleData: MutableLiveData<MutableList<ArticleData>> = MutableLiveData()
    val pageData: MutableLiveData<Int> = MutableLiveData()

    init {
        getData(0)
    }

    fun getData(page: Int) {
        viewModelScope.launch {
            pageData.postValue(page)
//            request { getQA(page) }
//                    .next {
//                        articleData.postValue(getRemoteData(this)?.datas)
//                    }
        }
    }

}