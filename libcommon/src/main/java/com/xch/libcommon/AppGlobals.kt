package com.xch.libcommon

import android.app.Application

lateinit var application: Application

fun getPackageName(): String = application.packageName