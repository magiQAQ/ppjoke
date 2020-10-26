package com.xch.ppjoke.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.xch.ppjoke.model.Destination
import java.nio.charset.Charset

object AppConfig {
    val destConfig: java.util.HashMap<String, Destination> by lazy {
        val content = parseFile("destination.json")
        JSON.parseObject(
            content,
            object :TypeReference<HashMap<String, Destination>>(){}
        )
    }

    private fun parseFile(fileName: String): String {
        val assets = AppGlobals.application.resources.assets
        return assets.open(fileName).readBytes().toString(Charset.defaultCharset())
    }
}