package com.xch.ppjoke.util

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import com.xch.ppjoke.navigator.FixFragmentNavigator

object NavGraphBuilder {
//    fun build(activity: FragmentActivity, childFragmentManager: FragmentManager, controller: NavController, containerId: Int) {
//        val provider = controller.navigatorProvider
//
//        //NavGraphNavigator也是页面路由导航器的一种，只不过他比较特殊。
//        //它只为默认的展示页提供导航服务,但真正的跳转还是交给对应的navigator来完成的
//    }
    fun build(fragmentActivity: FragmentActivity, childFragmentManager: FragmentManager ,controller: NavController, containerId: Int) {
        val provider = controller.navigatorProvider

//        val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
        // fragment的导航此处使用我们定制的FixFragmentNavigator，底部Tab切换时 使用hide()/show(),而不是replace()
        val fragmentNavigator = FixFragmentNavigator(fragmentActivity, fragmentActivity.supportFragmentManager, containerId)
        provider.addNavigator(fragmentNavigator)
        val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)

        val navGraph = NavGraph(NavGraphNavigator(provider))

        val destConfig = AppConfig.destConfig
        for (value in destConfig.values) {
            if (value.isFragment) {
                val destination = fragmentNavigator.createDestination()
                destination.className = value.clazzName
                destination.id = value.id
                destination.addDeepLink(value.pageUrl)

                navGraph.addDestination(destination)
            } else {
                val destination = activityNavigator.createDestination()
                destination.id = value.id
                destination.addDeepLink(value.pageUrl)
                destination.setComponentName(
                    ComponentName(
                        AppGlobals.getPackageName(),
                        value.clazzName
                    )
                )

                navGraph.addDestination(destination)
            }

            if (value.asStarter) {
                navGraph.startDestination = value.id
            }
        }

        controller.graph = navGraph
    }
}