package com.xch.libnetwork

import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

class JsonConvert : Convert<Any?>{
    override fun convert(response: String, type: Type): Any? {
        val jsonObject = JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if (data != null) {
            val data1 = data["data"]
            return JSON.parseObject(data1.toString(), type)
        }
        return null
    }

    override fun convert(response: String, clazz: Class<Any?>): Any? {
        val jsonObject = JSON.parseObject(response)
        val data = jsonObject.getJSONObject("data")
        if (data != null) {
            val data1 = data["data"]
            return JSON.parseObject(data1.toString(), clazz)
        }
        return null
    }
}