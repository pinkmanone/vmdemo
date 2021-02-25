package com.wc.learn.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<VDB : ViewDataBinding> : BaseActivity() {
    lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        initView()
    }

    abstract fun getLayoutRes(): Int

    open fun initView() {}
}