package com.wc.learn.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseVMFragment<VDB : ViewDataBinding, VM : BaseViewModel> : LazyFragment() {
    private val TAG = "wc tag " + javaClass.simpleName

    lateinit var binding: VDB
    lateinit var viewModel: VM

    override fun useDataBinding(): Boolean {
        return true
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        viewModel = getVM()
        viewModel.loadingLiveData.observe(this, Observer {
            showProgress(it)
        })

        //异常信息
        viewModel.errorLiveData.observe(this, Observer {
            activity?.showToast(it)
        })

        //异常码
        viewModel.errorCodeLiveData.observe(this, Observer {
            error(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun initView() {}

    open fun showProgress(show: Boolean) {}

    open fun getVariableId(): Int {
        return 0
    }

    open fun error(code: Int) {

    }

    @Suppress("UNCHECKED_CAST")
    private fun getVM(): VM {
        val viewModelClass: Class<BaseViewModel>
        val type = javaClass.genericSuperclass
        viewModelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel> //获取第1个注解即VM的注解类型
        } else {
            //使用父类的类型
            BaseViewModel::class.java
        }
        val viewModel = ViewModelProvider(requireActivity()).get(viewModelClass) as VM
        if (getVariableId() > 0) {
            binding.setVariable(getVariableId(), viewModel)
        }
        return viewModel
    }


}