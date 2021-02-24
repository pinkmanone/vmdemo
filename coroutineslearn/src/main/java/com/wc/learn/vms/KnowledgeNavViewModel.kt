package com.wc.learn.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wc.learn.base.BaseViewModel
import com.wc.learn.data.NavData
import com.wc.learn.data.next
import kotlinx.coroutines.launch

class KnowledgeNavViewModel : BaseViewModel() {

    val tagLiveData: MutableLiveData<List<NavData>> = MutableLiveData()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            request {
                getNavi()
            }.next {
                tagLiveData.postValue(getRemoteData(this))
            }
        }
    }
}