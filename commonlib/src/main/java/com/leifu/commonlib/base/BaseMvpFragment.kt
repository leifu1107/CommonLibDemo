package com.leifu.commonlib.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.leifu.commonlib.view.MultipleStatusView
import com.leifu.commonlib.view.dialog.LoadingUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 *创建人:雷富
 *创建时间:2019/6/5 16:16
 *描述:
 */
abstract class BaseMvpFragment<P : IBasePresenter> : BaseFragment(), IBaseView {

    var mPresenter: P? = null

    /**
     * 多种状态的 View 的切换
     */
    var mLayoutStatusView: MultipleStatusView? = null
    /**
     * 刷新布局
     */
    var smartRefreshLayout: SmartRefreshLayout? = null
    /**
     * 多种状态的 View 的切换
     */
    var currentPage: Int = 1//当前页数

    private var isLoadingMore = false

    private var isRefresh = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this)
        super.onViewCreated(view, savedInstanceState)
        //多种状态切换的view 重试点击事件 todo setOnClickListener会穿透点击事件
        mLayoutStatusView?.setOnRetryClickListener {
            mLayoutStatusView?.showLoading()
            initData()
        }
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    /**
     * 加载中
     */
    override fun showLoading() {
        if (isRefresh || isLoadingMore) {
            return
        }
//        mLayoutStatusView?.showLoading() ?:
        LoadingUtil.showLoading(mActivity, "加载中...")
    }

    /**
     * 取消加载
     */
    override fun dismissLoading() {
        LoadingUtil.dismissLoading()
    }


    /**
     * 无网络
     */
    override fun showNoNetwork() {
        mLayoutStatusView?.showNoNetwork()
        smartRefreshLayout?.finishRefresh()
        smartRefreshLayout?.finishLoadMore()
    }

    /**
     * 显示内容视图
     */
    override fun showContent() {
        mLayoutStatusView?.showContent()
    }

    /**
     * 显示错误视图
     */
    override fun showError() {
        mLayoutStatusView?.showError()
        smartRefreshLayout?.finishRefresh()
        smartRefreshLayout?.finishLoadMore()
    }


    /**
     * 显示错误提示
     */
    override fun showErrorMsg(msg: String) {
        ToastUtils.showShort(msg)
    }

    override fun finishRefreshAndLoadMore() {
        smartRefreshLayout?.finishRefresh()
        smartRefreshLayout?.finishLoadMore()
    }

    /**
     * 刷新和加载更多
     */
    open fun setOnRefreshLoadMore(mSmartRefreshLayout: SmartRefreshLayout?) {
        smartRefreshLayout = mSmartRefreshLayout
        mSmartRefreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                isLoadingMore = true
                currentPage++
                initData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                isRefresh = true
                currentPage = 1
                initData()
            }
        })
    }

    /**
     * 请求网络数据
     */
//    abstract fun loadData()

    /**
     * 创建Presenter
     */
    abstract fun createPresenter(): P
}