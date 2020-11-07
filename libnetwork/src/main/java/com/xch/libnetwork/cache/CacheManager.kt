package com.xch.libnetwork.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {
    fun <T> save(key: String, body: T) {
        val cache = Cache(key, toByteArray(body))
        CacheDatabase.database.cacheDao().save(cache)
    }

    fun getCache(key: String):Any? {
        val cache = CacheDatabase.database.cacheDao().getCache(key)
        if (cache?.data != null) {
            return toAny(cache.data)
        }
        return null
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

    private fun toAny(data: ByteArray): Any? {
        var bis: ByteArrayInputStream? = null
        var ois: ObjectInputStream? = null
        try {
            bis = ByteArrayInputStream(data)
            ois = ObjectInputStream(bis)
            return ois.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bis?.close()
            ois?.close()
        }
        return null
    }
}