package com.wc.learn.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wc.learn.base.BaseViewModel
import com.wc.learn.data.ArticleData
import com.wc.learn.data.next
import kotlinx.coroutines.launch

class TagArticleViewModel : BaseViewModel() {

    val pageLiveData: MutableLiveData<Int> = MutableLiveData()
    val articleLiveData: MutableLiveData<List<ArticleData>> = MutableLiveData()
    val endLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getData(page: Int, cid: Int) {
        viewModelScope.launch {
            pageLiveData.postValue(page + 1)
            request {
                getArticleByTag(page, cid)
            }.next {
                val datas = this.data?.datas
                if (datas == null || datas.size == 0) {
                    endLiveData.postValue(true)
                } else {
                    articleLiveData.postValue(getRemoteData(this)?.datas)
                }
            }
        }
    }

}