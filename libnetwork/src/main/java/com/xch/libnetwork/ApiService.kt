package com.xch.libnetwork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ApiService {

    val okHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .build()
    }

    lateinit var baseUrl: String
    lateinit var convert: Convert<*>

    fun init(baseUrl: String, convert: Convert<*>?) {
        this.baseUrl = baseUrl
        this.convert = convert ?: JsonConvert()
    }
}