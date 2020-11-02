package com.xch.libnetwork

class GetRequest<T>(url: String) : Request<T, GetRequest<T>>(url) {

    override fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request {
        return builder.get().url(createUrlFromParams(url, params)).build()
    }
}