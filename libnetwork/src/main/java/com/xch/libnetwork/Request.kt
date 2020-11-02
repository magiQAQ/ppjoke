package com.xch.libnetwork

import androidx.annotation.IntDef
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

abstract class Request<T, R>(protected val url: String) {
    protected val headers = HashMap<String, String>()
    protected val params = HashMap<String, Any>()
    protected lateinit var cacheKey: String

    companion object{
        //仅仅只访问本地缓存, 即便本地缓存不存在, 也不会发起网络请求
        const val CACHE_ONLY = 1

        //先访问缓存, 同时发起网络的请求, 成功后缓存到本地
        const val CACHE_FIRST = 2

        //仅仅只访问服务器, 不做任何存储
        const val NET_ONLY = 3

        //先访问网络, 成功后缓存本地
        const val NET_CACHE = 4
    }

    @IntDef(CACHE_ONLY, CACHE_FIRST, NET_ONLY, NET_CACHE)
    annotation class CacheStrategy

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
        getCall().enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                val response = Response<T>()
                response.message = e.message
                callback.onError(response)
            }

            override fun onResponse(call: Call, response: Response) {
                TODO("Not yet implemented")
            }
        })
    }

    fun execute(){

    }

    private fun getCall(): Call{
        val builder = okhttp3.Request.Builder();
        headers.forEach { (key, value) ->
            builder.addHeader(key, value)
        }
        val request = generateRequest(builder)
        return ApiService.okHttpClient.newCall(request)
    }

    protected abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request
}
