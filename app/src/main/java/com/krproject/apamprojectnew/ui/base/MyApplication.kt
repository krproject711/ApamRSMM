package com.krproject.apamprojectnew.ui.base

import android.app.Application
import android.os.Build
import android.os.StrictMode




class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }
}