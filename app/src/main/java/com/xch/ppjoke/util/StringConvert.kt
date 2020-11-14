package com.xch.ppjoke.util

fun convertFeedUgc(count: Int): String {
    return if (count < 10000) {
        count.toString()
    } else {
        "${count/10000}万"
    }
}