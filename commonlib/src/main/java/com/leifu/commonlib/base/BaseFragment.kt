package com.leifu.commonlib.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leifu.commonlib.net.response.EventBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:33
 *描述:
 */
abstract class BaseFragment : Fragment(), IBase {
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mView: View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(getLayoutId(), null)
        EventBus.getDefault().register(this)
        onCreateViewInit(mView, savedInstanceState)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        //在懒加载前调用
        lazyLoadDataPrepared()
    }


    override fun onHiddenChanged(hidden: Boolean) {//单个fragment
        super.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {//结合viewpager
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataPrepared()
        }
    }

    private fun lazyLoadDataPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            initData()
            hasLoadData = true
        }
    }

    /**
     * 初始化 View
     */
    open fun onCreateViewInit(mView: View?, savedInstanceState: Bundle?) {}

    /**
     * 重新登录
     */
    override fun onLogin() {
        (mActivity as BaseActivity).onLogin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEvent(eventBean: EventBean) {
    }

}