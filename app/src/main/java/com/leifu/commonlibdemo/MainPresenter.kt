package com.leifu.commonlibdemo

import com.leifu.commonlib.base.BaseRxPresenter
import com.leifu.commonlib.net.RetrofitManager
import com.leifu.commonlib.net.response.BaseBean
import com.leifu.commonlib.net.rx.FlowableSubscriberManager
import com.leifu.commonlib.net.rx.RxManage

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class MainPresenter : BaseRxPresenter<MainContract.View>(), MainContract.Presenter {

    override fun getData() {
        addSubscription(
            RetrofitManager.apiService.getData()
                .compose(RxManage.rxSchedulerFlowableHelper())
                .compose(RxManage.handleFlowableResult<BaseBean>())
                .subscribeWith(
                    object : FlowableSubscriberManager<BaseBean>(mView, true) {
                        override fun onNext(t: BaseBean) {
                            super.onNext(t)
                            this@MainPresenter.mView?.showData(t)
                        }
                    })
        )
    }
}