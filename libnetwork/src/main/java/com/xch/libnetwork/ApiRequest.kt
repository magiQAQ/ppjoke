package com.xch.libnetwork

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.IntDef
import androidx.arch.core.executor.ArchTaskExecutor
import com.xch.libnetwork.cache.CacheManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.io.Serializable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
abstract class ApiRequest<T, R>(protected val url: String) {
    protected val headers = HashMap<String, String>()
    protected val params = HashMap<String, Any>()
    protected lateinit var cacheKey: String
    protected var cacheStrategy: Int = 3

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

    fun cacheStrategy(@CacheStrategy cacheStrategy: Int): R {
        this.cacheStrategy = cacheStrategy
        return this as R
    }

    fun cacheKey(key: String): R {
        cacheKey = key
        return this as R
    }

    /**
     * 异步请求
     * @param callback 结果的回调
     */
    @SuppressLint("RestrictedApi")
    fun execute(callback: JsonCallback<T>){
        // 如果访问策略是只访问网络就不走缓存
        if (cacheStrategy != NET_ONLY) {
            ArchTaskExecutor.getIOThreadExecutor().execute{
                val response = readCache()
                callback.onCacheSuccess(response)
            }
        }

        // 如果访问策略是只访问缓存就不走网络
        if (cacheStrategy != CACHE_ONLY) {
            getCall().enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    val apiResponse = ApiResponse<T>()
                    apiResponse.message = e.message
                    callback.onError(apiResponse)
                }

                override fun onResponse(call: Call, response: Response) {
                    val apiResponse = parseResponse(response, callback)
                    if (apiResponse.success) {
                        callback.onError(apiResponse)
                    } else {
                        callback.onSuccess(apiResponse)
                    }
                }
            })
        }
    }

    /**
     * 同步请求
     * 因为是同步请求, 所以访问策略只能是只访问网络和只访问缓存中的其中一种
     * 默认只访问网络
     * @return 服务器返回
     */
    fun execute(): ApiResponse<T>?{
        if (cacheStrategy == CACHE_ONLY) {
            return readCache()
        }
        try {
            val response = getCall().execute()
            return parseResponse(response, null)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getCall(): Call{
        val builder = okhttp3.Request.Builder()
        headers.forEach { (key, value) ->
            builder.addHeader(key, value)
        }
        val request = generateRequest(builder)
        return ApiService.okHttpClient.newCall(request)
    }

    private fun readCache() : ApiResponse<T>{
        val key = generateCacheKey()
        val cache = CacheManager.getCache(key)
        val result = ApiResponse<T>()
        result.success = cache == null
        result.status = 304
        result.message = "缓存获取${if (result.success) "成功" else "失败"}"
        result.body = cache as T?
        return result
    }

    private var mType : Type? = null
    private var mClazz : Class<*>? = null

    private fun parseResponse(response: Response, callback: JsonCallback<T>?) : ApiResponse<T>{
        var message = ""
        val status = response.code
        var success = response.isSuccessful
        val result = ApiResponse<T>()
        val convert = ApiService.convert
        try {
            val content = response.body?.string()?:""
            if (success) {
                when {
                    callback != null -> {
                        val type = callback.javaClass.genericSuperclass as ParameterizedType
                        val argument = type.actualTypeArguments[0]
                        result.body = convert.convert(content, argument) as T?
                    }
                    mType != null -> {
                        result.body = convert.convert(content, mType!!) as T?
                    }
                    mClazz != null -> {
                        result.body = convert.convert(content, mClazz!!) as T?
                    }
                    else -> {
                        Log.e("request", "parseResponse: 无法解析")
                    }
                }
            } else {
                message = content
            }
        } catch (e: Exception) {
            message = e.message?:""
            success = false
        }

        result.success = success
        result.status = status
        result.message = message

        if(result.success && result.body!=null && result.body is Serializable && cacheStrategy != NET_ONLY) {
            saveCacheStrategy(result.body!!)
        }

        return result
    }

    private fun saveCacheStrategy(body: T) {
        val key = generateCacheKey()
        CacheManager.save(key, body)
    }

    // 用户没有传入cacheKey就自动生成一个
    private fun generateCacheKey() :String{
        if (cacheKey.isEmpty()) {
            cacheKey = createUrlFromParams(url, params)
        }
        return cacheKey
    }

    protected abstract fun generateRequest(builder: okhttp3.Request.Builder): okhttp3.Request
}
