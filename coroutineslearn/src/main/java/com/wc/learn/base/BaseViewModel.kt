package com.wc.learn.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wc.learn.data.NetData
import com.wc.learn.network.NetException
import com.wc.learn.network.Network
import com.wc.learn.network.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

open class BaseViewModel : ViewModel() {

    val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorCodeLiveData: MutableLiveData<Int> = MutableLiveData()

    init {
        loadingLiveData.value = false
    }
    /**
     * 简单的请求，没有加try-catch捕获异常，使用的时候切记！！！！！需要手动try-catch或使用runCatching方法捕获
     * @receiver BaseViewModel
     * @param request [@kotlin.ExtensionFunctionType] SuspendFunction1<Network, NetData<T>?>
     * @return NetData<T>
     */
    suspend fun <T> requestSimple(request: suspend Network.() -> NetData<T>?): NetData<T> {
        /*withContext表示挂起块  配合Retrofit声明的suspend函数执行 该块会挂起直到里面的网络请求完成 最一行就是返回值*/
        val response = withContext(Dispatchers.IO) {
            /*扩展函数可以很方便的解析出我们想要的数据  接口很多的情况下下可以节省不少无用代码*/
            request(service)
        } ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
        if (response.errorCode != 0) {
            throw  NetException(response.errorCode, response.errorMsg ?: "")
        }
        return response;
    }

    suspend fun <T> request(
            showLoading: Boolean = false,
            doRequest: suspend Network.() -> NetData<T>?
    ): NetData<T> {
        return try {
            if (showLoading) {
                showLoading()
            }
            requestSimple(doRequest)
        } catch (e: Exception) {
            errorLiveData.postValue(e.message)
            NetData<T>().apply {
                exception = e
            }
        } finally {
            closeLoading()
        }
    }

    private fun showLoading() {
        loadingLiveData.value = true
    }

    private fun closeLoading() {
        loadingLiveData.value = false
    }


    /**
     * 请求数据,处理结果
     */
    fun <T> getRemoteData(data: NetData<T>): T? {
        if (data.errorCode == 0) {
            return data.data!!
        } else {
            errorCodeLiveData.postValue(data.errorCode)
            errorLiveData.postValue(data.errorMsg)
        }
        return null
    }

}