package com.xch.libnetwork

import okhttp3.Request

class GetRequest<T>(url: String) : ApiRequest<T, GetRequest<T>>(url) {

    override fun generateRequest(builder: Request.Builder): Request {
        return builder.get().url(createUrlFromParams(url, params)).build()
    }
}