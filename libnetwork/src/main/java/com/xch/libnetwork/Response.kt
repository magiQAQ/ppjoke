package com.xch.libnetwork

class Response<T> {
    var success = false
    var status = 0
    var message: String? = null
    var body: T? = null
}