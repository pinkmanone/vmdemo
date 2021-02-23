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
//        // 设置是否开启热更新能力，默认为true
//
//        // 设置是否开启热更新能力，默认为true
//        Beta.enableHotfix = true
//// 设置是否自动下载补丁，默认为true
//// 设置是否自动下载补丁，默认为true
//        Beta.canAutoDownloadPatch = true
//// 设置是否自动合成补丁，默认为true
//// 设置是否自动合成补丁，默认为true
//        Beta.canAutoPatch = true
//// 设置是否提示用户重启，默认为false
//// 设置是否提示用户重启，默认为false
//        Beta.canNotifyUserRestart = false
//// 补丁回调接口
//// 补丁回调接口
//        Beta.betaPatchListener = object : BetaPatchListener {
//            override fun onPatchReceived(patchFile: String) {}
//            override fun onDownloadReceived(savedLength: Long, totalLength: Long) {}
//            override fun onDownloadSuccess(msg: String) {}
//            override fun onDownloadFailure(msg: String) {}
//            override fun onApplySuccess(msg: String) {}
//            override fun onApplyFailure(msg: String) {}
//            override fun onPatchRollback() {}
//        }
//        Bugly.setIsDevelopmentDevice(getApplication(), false) //是否为开发设备（测试的时候设置为true）
//
//        Bugly.init(getApplication(), "511f62b402", true);

    }


}