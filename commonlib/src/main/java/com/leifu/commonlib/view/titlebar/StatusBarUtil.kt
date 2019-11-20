package com.leifu.commonlib.view.titlebar


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

/**
 * Function: 状态栏工具类(状态栏文字颜色)
 * Description:
 * 1、修改状态栏黑白字 功能逻辑--参考 https://github.com/QMUI/QMUI_Android  QMUIStatusBarHelper类
 */
@SuppressLint("StaticFieldLeak")
object StatusBarUtil {

    val STATUS_BAR_TYPE_DEFAULT = 0
    val STATUS_BAR_TYPE_MI_UI = 1
    val STATUS_BAR_TYPE_FLY_ME = 2
    val STATUS_BAR_TYPE_ANDROID_M = 3

    /**
     * 判断系统是否支持状态栏文字及图标颜色变化
     *
     * @return
     */
    val isSupportStatusBarFontChange: Boolean
        get() = (RomUtil.getMIUIVersionCode() >= 6 || RomUtil.getFlymeVersionCode() >= 4
                || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

    /**
     * 获取状态栏高度
     *
     * @return
     */
    val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId)
            }
            return result
        }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    var contentViewGroup: View? = null

    /**
     * 设置状态栏浅色模式--黑色字体图标，
     *
     * @param activity
     * @return
     */
    fun setStatusBarLightMode(activity: Activity): Int {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏显示状态栏
        var result = STATUS_BAR_TYPE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //MIUI 9版本开始状态栏文字颜色恢复为系统原生方案-为防止反复修改先进行6.0方案
            if (setStatusBarModeForAndroidM(activity.window, true)) {
                result = STATUS_BAR_TYPE_ANDROID_M
            }
            if (setStatusBarModeForMIUI(activity.window, true)) {
                result = STATUS_BAR_TYPE_MI_UI
            } else if (setStatusBarModeForFlyMe(activity.window, true)) {
                result = STATUS_BAR_TYPE_FLY_ME
            }
        }
        return result
    }

    /**
     * 设置状态栏深色模式--白色字体图标，
     *
     * @param activity
     * @return
     */
    fun setStatusBarDarkMode(activity: Activity): Int {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏显示状态栏
        var result = STATUS_BAR_TYPE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //MIUI 9版本开始状态栏文字颜色恢复为系统原生方案-为防止反复修改先进行6.0方案
            if (setStatusBarModeForAndroidM(activity.window, false)) {
                result = STATUS_BAR_TYPE_ANDROID_M
            }
            if (setStatusBarModeForMIUI(activity.window, false)) {
                result = STATUS_BAR_TYPE_MI_UI
            } else if (setStatusBarModeForFlyMe(activity.window, false)) {
                result = STATUS_BAR_TYPE_FLY_ME
            }
        }
        return result
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window   需要设置的窗口
     * @param darkText 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun setStatusBarModeForMIUI(window: Window?, darkText: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                var darkModeFlag = 0
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField =
                    clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (darkText) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag)
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag)
                }
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window   需要设置的窗口
     * @param darkText 是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private fun setStatusBarModeForFlyMe(window: Window?, darkText: Boolean): Boolean {
        var result = false
        if (window != null) {
            try {
                val lp = window.attributes
                val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
                darkFlag.isAccessible = true
                meizuFlags.isAccessible = true
                val bit = darkFlag.getInt(null)
                var value = meizuFlags.getInt(lp)
                if (darkText) {
                    value = value or bit
                } else {
                    value = value and bit.inv()
                }
                meizuFlags.setInt(lp, value)
                window.attributes = lp
                result = true
            } catch (e: Exception) {

            }

        }
        return result
    }

    /**
     * 设置原生Android 6.0以上系统状态栏
     *
     * @param window
     * @param darkText 是否把状态栏字体及图标颜色设置为深色
     * @return
     */
    private fun setStatusBarModeForAndroidM(window: Window, darkText: Boolean): Boolean {
        var result = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var systemUi = if (darkText) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            systemUi = changeStatusBarModeRetainFlag(window, systemUi)
            window.decorView.systemUiVisibility = systemUi
            result = true
        }
        return result
    }

    @TargetApi(23)
    private fun changeStatusBarModeRetainFlag(window: Window, out: Int): Int {
        var out = out
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_FULLSCREEN)
        //隐藏导航栏按键
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        //隐藏导航栏背景View
        out = retainSystemUiFlag(window, out, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        return out
    }

    fun retainSystemUiFlag(window: Window, out: Int, type: Int): Int {
        var out = out
        val now = window.decorView.systemUiVisibility
        if (now and type == type) {
            out = out or type
        }
        return out
    }

    //---------------------------------状态栏透明半透明效果-------------------------------------------------------

    /**
     * 全透状态栏
     */
    fun setStatusBarFullTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    fun setHalfTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            val decorView = activity.window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    fun setFitSystemWindow(activity: Activity, fitSystemWindow: Boolean) {
        if (contentViewGroup == null) {
            contentViewGroup = (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
        }
        contentViewGroup!!.fitsSystemWindows = fitSystemWindow
    }

    /**
     * 为了兼容4.4的抽屉布局->透明状态栏
     */
    fun setDrawerLayoutFitSystemWindow(activity: Activity) {
        if (Build.VERSION.SDK_INT == 19) {//19表示4.4
            val statusBarHeight = statusBarHeight
            if (contentViewGroup == null) {
                contentViewGroup = (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
            }
            if (contentViewGroup is DrawerLayout) {
                val drawerLayout = contentViewGroup as DrawerLayout?
                drawerLayout!!.clipToPadding = true
                drawerLayout.fitsSystemWindows = false
                for (i in 0 until drawerLayout.childCount) {
                    val child = drawerLayout.getChildAt(i)
                    child.fitsSystemWindows = false
                    child.setPadding(0, statusBarHeight, 0, 0)
                }
            }
        }
    }

    //-----------------------view动态设置状态栏高度
    /** 增加View的paddingTop,增加的值为状态栏高度  */
    fun setPadding(context: Context, view: View) {
        if (Build.VERSION.SDK_INT >= 19) {
            view.setPadding(
                view.paddingLeft, view.paddingTop + statusBarHeight,
                view.paddingRight, view.paddingBottom
            )
        }
    }

    /** 增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度) */
    fun setPaddingSmart(context: Context, view: View) {
        if (Build.VERSION.SDK_INT >= 19) {
            val lp = view.layoutParams
            if (lp != null && lp.height > 0) {
                lp.height += statusBarHeight//增高 }
                view.setPadding(
                    view.paddingLeft,
                    view.paddingTop + statusBarHeight,
                    view.paddingRight,
                    view.paddingBottom
                )
            }
        }

        /** 增加View的高度以及paddingTop,增加的值为状态栏高度.一般是在沉浸式全屏给ToolBar用的  */
        fun setHeightAndPadding(context: Context, view: View) {
            if (Build.VERSION.SDK_INT >= 19) {
                val lp = view.layoutParams
                lp.height += statusBarHeight//增高
                view.setPadding(
                    view.paddingLeft,
                    view.paddingTop + statusBarHeight,
                    view.paddingRight,
                    view.paddingBottom
                )
            }
        }

        /** 增加View上边距（MarginTop）一般是给高度为 WARP_CONTENT 的小控件用的 */
        fun setMargin(context: Context, view: View) {
            if (Build.VERSION.SDK_INT >= 19) {
                val lp = view.layoutParams
                if (lp is ViewGroup.MarginLayoutParams) {
                    lp.topMargin += statusBarHeight//增高 }
                    view.layoutParams = lp
                }
            }
        }
    }

}
