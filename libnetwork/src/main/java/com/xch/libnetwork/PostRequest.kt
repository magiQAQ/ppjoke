package com.xch.libnetwork

import okhttp3.FormBody

class PostRequest<T>(url: String) : Request<T, PostRequest<T>>(url){
    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        val bodyBuilder = FormBody.Builder()
        params.forEach { (key, value) ->
            bodyBuilder.add(key, value.toString())
        }
        return builder.post(bodyBuilder.build()).url(url).build()
    }
}