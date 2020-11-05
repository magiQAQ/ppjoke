package com.xch.libnetwork.cache

import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

object CacheManager {
    fun <T> save(key: String, body: T) {
        val cache = Cache(key, toByteArray(body))
    }

    private fun <T> toByteArray(body: T): ByteArray {
        var bos: ByteArrayOutputStream? = null
        var oos: ObjectOutputStream? = null
        try{
            bos = ByteArrayOutputStream()
            oos = ObjectOutputStream(bos)
            oos.writeObject(body)
            oos.flush()
            return bos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bos?.close()
            oos?.close()
        }
        return ByteArray(0)
    }
}