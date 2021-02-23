package com.wc.learn.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wc.learn.data.NetData
import kotlinx.coroutines.*
import java.lang.reflect.ParameterizedType

abstract class BaseFragment : LazyFragment() {
    private val TAG = "wc tag " + javaClass.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "on create")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "on start")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "on resume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "on pause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "on stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "on destroy")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun initView() {}

    open fun showProgress(show: Boolean) {}

    open fun error() {}

    private fun dealException(e: Exception) {
        error()
    }
}