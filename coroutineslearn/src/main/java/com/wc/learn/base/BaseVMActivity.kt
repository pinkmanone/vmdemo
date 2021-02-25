package com.wc.learn.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseVMActivity<VDB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    lateinit var binding: VDB
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        viewModel = getVM()
        viewModel.loadingLiveData.observe(this, Observer {
            showProgress(it)
        })

        viewModel.errorLiveData.observe(this, Observer {
            showToast(it)
        })
        initView()
    }

    protected abstract fun getLayoutRes(): Int

    open fun initView() {}

    open fun showProgress(show: Boolean) {}

    @Suppress("UNCHECKED_CAST")
    private fun getVM(): VM {
        val viewModelClass: Class<BaseViewModel>
        val type = javaClass.genericSuperclass
        viewModelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            BaseViewModel::class.java
        }
        val viewModel = ViewModelProvider(this).get(viewModelClass) as VM
        if (getVariableId() > 0) {
            binding.setVariable(getVariableId(), viewModel)
        }
        return viewModel
    }

    open fun getVariableId(): Int {
        return 0
    }

}