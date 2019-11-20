package com.leifu.commonlib.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.leifu.commonlib.net.response.EventBean
import com.leifu.commonlib.util.ActivityManager
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
abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.applicationContext
        mActivity = this
        ActivityManager.instance.addActivity(this)
        EventBus.getDefault().register(this)
        //状态栏透明
        StatusBarUtil.setStatusBarFullTransparent(this)
        //状态栏白色字体图标
        StatusBarUtil.setStatusBarDarkMode(this)
        setContentView(getLayoutId())
        initView(savedInstanceState)
        initData()
    }

    /**
     *  加载布局
     */
    abstract fun getLayoutId(): Int

    /**
     *  initView
     */
    open fun initView(savedInstanceState: Bundle?) {
    }

    /**
     * 初始化数据
     */
    abstract fun initData()


    /**
     *设置中心title和返回监听
     */
    open fun setTitleText(title: String, rightBtnVisible: Boolean) {
        centerTitle.text = title
        if (rightBtnVisible) {
            btnRight.visibility = View.VISIBLE
        }
        btnBack.setOnClickListener { finish() }
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.instance.removeActivity(this)
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEvent(eventBean: EventBean) {
    }


    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }
}