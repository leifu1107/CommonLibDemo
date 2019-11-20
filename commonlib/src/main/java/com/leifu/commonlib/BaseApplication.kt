package com.leifu.commonlib

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import kotlin.properties.Delegates


/**
 *创建人:雷富
 *创建时间:2019/6/5 14:24
 *描述:
 */
open class BaseApplication : Application() {

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initConfig()
    }

    private fun initConfig() {
    }

}