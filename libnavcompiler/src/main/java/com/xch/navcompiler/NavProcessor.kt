package com.xch.navcompiler

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.google.auto.service.AutoService
import com.xch.navannotation.ActivityDestination
import com.xch.navannotation.FragmentDestination
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation
import kotlin.math.abs

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(
    "com.xch.navannotation.ActivityDestination",
    "com.xch.navannotation.FragmentDestination"
)
internal class NavProcessor : AbstractProcessor() {

    private lateinit var messager: Messager
    private lateinit var filer: Filer
    companion object{
        private const val OUTPUT_FILE_NAME = "destination.json"
    }

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        messager = processingEnv.messager
        filer = processingEnv.filer
    }

    override fun process(annotations: Set<TypeElement?>, roundEnv: RoundEnvironment): Boolean {
        val fragmentElements: Set<Element> =
            roundEnv.getElementsAnnotatedWith(FragmentDestination::class.java)
        val activityElements: Set<Element> =
            roundEnv.getElementsAnnotatedWith(ActivityDestination::class.java)
        if (fragmentElements.isNotEmpty() || activityElements.isNotEmpty()) {
            val destMap = HashMap<String, JSONObject>()
            handleDestination(fragmentElements, FragmentDestination::class.java, destMap)
            handleDestination(activityElements, ActivityDestination::class.java, destMap)

            //生成文件至app/src/main/assets路径下
            try {
                val resource =
                    filer.createResource(StandardLocation.CLASS_OUTPUT, "", OUTPUT_FILE_NAME)
                val resourcePath = resource.toUri().path
                messager.printMessage(Diagnostic.Kind.NOTE, "resourcePath:$resourcePath")

                val appPath = resourcePath.substring(0, resourcePath.indexOf("app")+4)
                val assetsPath = appPath + "src/main/assets/"

                val file = File(assetsPath)
                if (!file.exists()) {
                    file.mkdirs()
                }

                val outputFile = File(file, OUTPUT_FILE_NAME)
                if (outputFile.exists()){
                    outputFile.delete()
                }
                outputFile.createNewFile()
                val content = JSON.toJSONString(destMap)
                outputFile.writeText(content)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return true
    }

    private fun handleDestination(
        elements: Set<Element>,
        annotationClazz: Class<out Annotation>,
        destMap: HashMap<String, JSONObject>
    ) {
        elements.forEach { element ->
            element as TypeElement
            var pageUrl = ""
            val clazzName = element.qualifiedName.toString()
            val id = abs(clazzName.hashCode())
            var needLogin = false
            var asStarter = false
            var isFragment = false

            when (val annotation = element.getAnnotation(annotationClazz)) {
                is FragmentDestination -> {
                    pageUrl = annotation.pageUrl
                    asStarter = annotation.asStarter
                    needLogin = annotation.needLogin
                    isFragment = true
                }
                is ActivityDestination -> {
                    pageUrl = annotation.pageUrl
                    asStarter = annotation.asStarter
                    needLogin = annotation.needLogin
                    isFragment = false
                }
            }

            if (destMap.containsKey(pageUrl)) {
                messager.printMessage(Diagnostic.Kind.ERROR, "不同的页面不允许使用相同的pageUrl:$clazzName")
            } else {
                val jsonObject = JSONObject()
                jsonObject["id"] = id
                jsonObject["needLogin"] = needLogin
                jsonObject["asStarter"] = asStarter
                jsonObject["pageUrl"] = pageUrl
                jsonObject["clazzName"] = clazzName
                jsonObject["isFragment"] = isFragment
                destMap[pageUrl] = jsonObject
            }
        }

    }


}