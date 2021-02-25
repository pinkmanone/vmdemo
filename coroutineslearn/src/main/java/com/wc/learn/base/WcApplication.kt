package com.wc.learn.base

import android.app.Application
//import com.tencent.bugly.Bugly
//import com.tencent.bugly.beta.Beta
//import com.tencent.bugly.beta.interfaces.BetaPatchListener
//import com.tencent.bugly.beta.tinker.TinkerManager.getApplication


class WcApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {

    }


}