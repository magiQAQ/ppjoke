package com.xch.ppjoke.model

data class BottomBar(
    val activeColor: String,
    val inActiveColor: String,
    val tabs: List<Tab>,
    val selectTab: Int
) {
    data class Tab(
        val enable: Boolean,
        val index: Int,
        val pageUrl: String,
        val size: Int,
        val tintColor: String?,
        val title: String
    )
}