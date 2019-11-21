package com.leifu.commonlib.net.rx


import com.leifu.commonlib.base.IBaseView
import com.leifu.commonlib.net.except.ExceptionHandle
import com.leifu.commonlib.util.LogUtil
import io.reactivex.subscribers.ResourceSubscriber

/**
 * 创建人:雷富
 * 创建时间:2018/7/30 17:38
 * 描述:
 */

abstract class FlowableSubscriberManager<T> : ResourceSubscriber<T> {
    var mView: IBaseView? = null
    /**
     * 是否显示加载中
     */
    private var isShowLoading = true

    constructor(mView: IBaseView?) {
        this.mView = mView
    }

    constructor(mView: IBaseView?, isShowLoading: Boolean) {
        this.mView = mView
        this.isShowLoading = isShowLoading
    }


    override fun onStart() {
        super.onStart()
        LogUtil.e("onStart")
        if (isShowLoading) {
            mView?.showLoading()
        }
    }

    override fun onNext(t: T) {
        LogUtil.e("onNext")
        mView?.showContent()
        mView?.finishRefreshAndLoadMore()
        if (isShowLoading) {
            mView?.dismissLoading()
        }
    }

    override fun onComplete() {
        LogUtil.e("onComplete")
        if (isShowLoading) {
            mView?.dismissLoading()
        }
    }

    override fun onError(e: Throwable) {
        LogUtil.e("onError")
        if (isShowLoading) {
            mView?.dismissLoading()
        }
        mView?.finishRefreshAndLoadMore()
        ExceptionHandle.handleException(e, mView)
    }
}
