package com.xch.ppjoke.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.xch.ppjoke.R
import com.xch.ppjoke.util.AppConfig.bottomBarConfig
import com.xch.ppjoke.util.AppConfig.destConfig

@SuppressLint("RestrictedApi")
internal class AppBottomBar: BottomNavigationView{

    companion object {
        val icons: IntArray = intArrayOf(
            R.drawable.icon_tab_home, R.drawable.icon_tab_sofa, R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find, R.drawable.icon_tab_mine
        )
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int): super(context, attributeSet, defStyleAttr)

    init {
        val config = bottomBarConfig
        val states = arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf())
        val colors = intArrayOf(
            Color.parseColor(config.activeColor),
            Color.parseColor(config.inActiveColor)
        )
        val colorStateList = ColorStateList(states, colors)
        itemIconTintList = colorStateList
        itemTextColor = colorStateList
        //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
        //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
        //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
        //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        // 为bottomNavigationView添加按钮和图标
        val tabs = config.tabs
        for (tab in tabs) {
            if (!tab.enable) continue
            val itemId = getItemId(tab.pageUrl)
            if (itemId < 0) continue
            val menuItem = menu.add(0, itemId, tab.index, tab.title)
            menuItem.setIcon(icons[tab.index])
        }
        // 在添加完成后再为icon设置大小
        var index = 0
        for (tab in tabs) {
            if (!tab.enable) continue
            val itemId = getItemId(tab.pageUrl)
            if (itemId < 0) continue

            val iconSize = dp2dx(tab.size)
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(index) as BottomNavigationItemView
            itemView.setIconSize(iconSize)

            if (tab.title.isEmpty()) {
                val tintColor = if (tab.tintColor.isNullOrEmpty() ) Color.parseColor("#ff678f") else Color.parseColor(tab.tintColor)
                itemView.setIconTintList(ColorStateList.valueOf(tintColor))
                itemView.setShifting(false)
            }
            index++
        }
        // bottomNavigationView默认选中项
        if (config.selectTab != 0) {
            val selectTab = tabs[config.selectTab]
            if (selectTab.enable) {
                val itemId = getItemId(selectTab.pageUrl)
                post { selectedItemId = itemId }
            }
        }
    }

    private fun dp2dx(size: Int): Int {
        return (context.resources.displayMetrics.density * size + 0.5).toInt()
    }

    private fun getItemId(pageUrl: String): Int {
        val (_, _, id) = destConfig[pageUrl] ?: return -1
        return id
    }
}