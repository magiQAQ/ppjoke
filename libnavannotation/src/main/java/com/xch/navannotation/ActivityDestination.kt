package com.xch.navannotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ActivityDestination(
    val pageUrl: String,
    val needLogin: Boolean = false,
    val asStarter: Boolean = false
)