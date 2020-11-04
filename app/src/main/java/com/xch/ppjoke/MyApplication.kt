package com.xch.ppjoke

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        com.xch.libcommon.AppGlobals.application = this
    }
}