package com.xch.libnetwork

abstract class JsonCallback<T> {
    fun onSuccess(response: Response<T>) {

    }

    fun onError(response: Response<T>) {

    }

    fun onCacheSuccess(response: Response<T>) {

    }
}