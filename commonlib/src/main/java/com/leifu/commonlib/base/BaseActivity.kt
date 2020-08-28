package com.leifu.commonlib.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ReflectUtils
import com.leifu.commonlib.net.response.EventBean
import com.leifu.commonlib.util.LogUtil
import com.leifu.commonlib.view.titlebar.StatusBarUtil
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseActivity : AppCompatActivity(), IBase {

    lateinit var mContext: Context
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.applicationContext
        mActivity = this
        EventBus.getDefault().register(this)
        //状态栏透明
        StatusBarUtil.setStatusBarFullTransparent(this)
        //状态栏白色字体图标
        StatusBarUtil.setStatusBarDarkMode(this)
        setContentView(getLayoutId())
        initData()
    }


    /**
     *设置中心title和返回监听
     */
    open fun setTitleText(title: String) {
        centerTitle.text = title
        btnBack.setOnClickListener { finish() }
    }

    /**
     * 重新登录
     */
    override fun onLogin() {
//        SpUtil.removeUseData()
        //todo 注意目录级别和包名等
        val any = ReflectUtils.reflect(applicationInfo.processName + ".TextActivity")
            .get<Class<Activity>>()
        ActivityUtils.startActivity(any)
//        ActivityUtils.finishOtherActivities(any)
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

        LogUtil.e("onDestroy")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEvent(eventBean: EventBean) {
    }
}