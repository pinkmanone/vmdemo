package com.wc.learn

import android.app.Application
import android.content.Context

class WApp : Application() {


    override fun onCreate() {
        super.onCreate()
        application = this
    }


    fun getAppContext(): Context? {
        return getApp()?.applicationContext
    }

    companion object {
        var application: WApp? = null
        fun getApp(): WApp? {
            return if (application == null) {
                throw NullPointerException("App is not registered in the manifest")
            } else {
                application
            }
        }
    }
}