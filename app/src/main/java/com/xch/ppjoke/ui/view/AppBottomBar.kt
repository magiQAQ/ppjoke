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
import com.xch.ppjoke.util.AppConfig.bottomBar
import com.xch.ppjoke.util.AppConfig.destConfig

internal class AppBottomBar : BottomNavigationView {

    companion object {
        val icons: IntArray = intArrayOf(
            R.drawable.icon_tab_home, R.drawable.icon_tab_sofa, R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find, R.drawable.icon_tab_mine
        )
    }

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    @SuppressLint("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val bottomBar = bottomBar
        val tabs = bottomBar.tabs
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_selected)
        states[1] = intArrayOf()
        val colors = intArrayOf(
            Color.parseColor(bottomBar.activeColor),
            Color.parseColor(bottomBar.inActiveColor)
        )
        val colorStateList = ColorStateList(states, colors)
        itemIconTintList = colorStateList
        itemTextColor = colorStateList
        labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        selectedItemId = bottomBar.selectTab
        for (i in tabs.indices) {
            val tab = tabs[i]
            if (!tab.enable) continue
            val id = getId(tab.pageUrl)
            val item = menu.add(0, 0, tab.index, tab.title)
            item.setIcon(icons[i])

        }
        for (i in tabs.indices) {
            val tab = tabs[i]
            val iconSize = dp2dx(tab.size)
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(tab.index) as BottomNavigationItemView
            itemView.setIconSize(iconSize)

        }
    }

    private fun dp2dx(size: Int): Int {
        return (context.resources.displayMetrics.density * size + 0.5).toInt()
    }

    private fun getId(pageUrl: String): Int {
        val (_, _, id) = destConfig[pageUrl] ?: return -1
        return id
    }
}