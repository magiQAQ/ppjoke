package com.xch.libcommon

private val metrics by lazy { application.resources.displayMetrics }
fun dp2px(dpValue: Int): Int {
    return (metrics.density * dpValue + 0.5f).toInt()
}

fun getScreenWidth(): Int {
    return metrics.widthPixels
}

fun getScreenHeight(): Int {
    return metrics.heightPixels
}