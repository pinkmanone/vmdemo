package com.wc.learn.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wc.learn.base.BaseViewModel
import com.wc.learn.data.KnowledgeTag
import com.wc.learn.data.next
import kotlinx.coroutines.launch

class KnowledgeTreeViewModel : BaseViewModel() {

    val tagLiveData: MutableLiveData<List<KnowledgeTag>> = MutableLiveData()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            request {
                getKnowledgeTree()
            }.next {
                tagLiveData.postValue(getRemoteData(this))
            }
        }
    }
}