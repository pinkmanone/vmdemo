package com.wc.learn.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

open class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    open fun showProgress(show: Boolean) {}

    open fun error() {}

    private fun dealException(e: Exception) {
        error()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}