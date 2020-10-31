package com.xch.ppjoke.navigator

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("fixfragment")
class FixFragmentNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    companion object{
        val TAG = this::class.simpleName
    }

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (manager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already"
                        + " saved its state"
            )
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = context.packageName + className
        }

        // 这里注释掉原有的每次都会初始化fragment的逻辑
//        val frag = instantiateFragment(
//            mContext, mFragmentManager,
//            className, args
//        )
//        frag.arguments = args

        // 获取到下一个需要跳转的fragment实例
        val tag = className.substring(className.lastIndexOf('.') + 1)
        var nextFragment = manager.findFragmentByTag(tag)
        if (nextFragment == null) {
            nextFragment = instantiateFragment(context, manager, className, args)
        }
        nextFragment.arguments = args

        val ft = manager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }

        // 先隐藏所有fragment实例
        val fragments = manager.fragments
        fragments.forEach { ft.hide(it) }

        // 该容器中没有这个fragment时才会添加
        if (!nextFragment.isAdded) {
            ft.add(containerId, nextFragment, tag)
        }

        // 这里注释掉原有的替换逻辑, 替换会导致fragment销毁, 不是正常app的逻辑
//        ft.replace(mContainerId, frag)

        ft.show(nextFragment)
        ft.setPrimaryNavigationFragment(nextFragment)

        @IdRes val destId = destination.id
        //mBackStack是private的, 只能通过反射获取

        val field = FragmentNavigator::class.java.getDeclaredField("mBackStack")
        field.isAccessible = true
        val mBackStack = field.get(this) as ArrayDeque<Int>
        val initialNavigation = mBackStack.isEmpty()
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopReplacement = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.lastOrNull() == destId)

        val isAdded: Boolean
        isAdded = if (initialNavigation) {
            true
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                manager.popBackStack(
                    generateBackStackName(mBackStack.size, mBackStack.last()),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
            }
            false
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
            true
        }
        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    private fun generateBackStackName(backStackindex: Int, destid: Int):String {
        return "$backStackindex-$destid"
    }

}