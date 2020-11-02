package com.xch.libnetwork

import java.io.UnsupportedEncodingException
import java.net.URLEncoder


fun createUrlFromParams(url: String, params: Map<String, Any>): String {
    val builder = StringBuilder(url)
    if (url.indexOf("?") > 0 || url.indexOf("&") > 0) {
        builder.append("&")
    } else {
        builder.append("?")
    }
    return try {
        params.forEach { (key, value) ->
            builder.append(key).append("=").append(URLEncoder.encode(value.toString(), "UTF-8"))
                .append("&")
        }
        builder.deleteAt(builder.lastIndex)
        builder.toString()
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
        url
    }
}