package com.xch.libnetwork

import okhttp3.FormBody
import okhttp3.Request

class PostRequest<T>(url: String) : ApiRequest<T, PostRequest<T>>(url){
    override fun generateRequest(builder: Request.Builder): Request {
        val bodyBuilder = FormBody.Builder()
        params.forEach { (key, value) ->
            bodyBuilder.add(key, value.toString())
        }
        return builder.post(bodyBuilder.build()).url(url).build()
    }
}