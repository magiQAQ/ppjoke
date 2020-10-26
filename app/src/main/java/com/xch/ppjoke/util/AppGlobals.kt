package com.xch.ppjoke.util

import com.xch.ppjoke.MyApplication

object AppGlobals {
    lateinit var application: MyApplication

    fun getPackageName(): String = application.packageName
}