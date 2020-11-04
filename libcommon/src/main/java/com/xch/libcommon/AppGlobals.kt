package com.xch.libcommon

import android.app.Application

object AppGlobals {
    lateinit var application: Application

    fun getPackageName(): String = application.packageName
}