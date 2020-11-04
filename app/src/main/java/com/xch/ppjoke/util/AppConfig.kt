package com.xch.ppjoke.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.xch.ppjoke.model.BottomBar
import com.xch.ppjoke.model.Destination
import java.nio.charset.Charset

object AppConfig {
    val destConfig: HashMap<String, Destination> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val content = parseFile("destination.json")
        JSON.parseObject(
            content,
            object :TypeReference<HashMap<String, Destination>>(){}
        )
    }

    val bottomBarConfig: BottomBar by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        val content = parseFile("main_tabs_config.json")
        JSON.parseObject(content, BottomBar::class.java)
    }

    private fun parseFile(fileName: String): String {
        val assets = com.xch.libcommon.AppGlobals.application.resources.assets
        return assets.open(fileName).readBytes().toString(Charset.defaultCharset())
    }
}