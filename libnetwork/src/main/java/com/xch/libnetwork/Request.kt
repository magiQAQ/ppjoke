package com.xch.libnetwork

import androidx.annotation.IntDef

abstract class Request<T, R> {
    protected val headers = HashMap<String, String>()
    protected val params = HashMap<String, Any>()
    protected lateinit var cacheKey: String

    @IntDef(CACHE_ONLY, CACHE_FIRST, NET_ONLY, NET_CACHE)
    annotation class CacheStrategy
    constructor(val url: String)

    fun addHeader(key: String, value: String): R {
        headers[key] = value
        return this as R
    }

    fun addParams(key: String, param: Any?): R {
        if (param == null) return this as R
        try {
            val field = param.javaClass.getField("TYPE")
            val clazz = field.get(null) as Class<*>
            if (clazz.isPrimitive) {
                params[key] = param
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return this as R
    }

    fun cacheKey(key: String): R {
        cacheKey = key
        return this as R
    }

    fun execute(callback: JsonCallback<T>){
        getCall()
    }

    fun execute(){

    }

    private fun getCall(){
        
    }
}

//仅仅只访问本地缓存, 即便本地缓存不存在, 也不会发起网络请求
const val CACHE_ONLY = 1

//先访问缓存, 同时发起网络的请求, 成功后缓存到本地
const val CACHE_FIRST = 2

//仅仅只访问服务器, 不做任何存储
const val NET_ONLY = 3

//先访问网络, 成功后缓存本地
const val NET_CACHE = 4
