package com.xch.ppjoke

import android.app.Application
import com.xch.ppjoke.util.AppGlobals

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppGlobals.application = this
    }
}