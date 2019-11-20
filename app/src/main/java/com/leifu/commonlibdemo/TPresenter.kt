package com.leifu.commonlibdemo

import com.leifu.commonlib.base.BaseRxPresenter
import com.leifu.commonlib.model.net.RetrofitManager
import com.leifu.commonlib.model.net.response.BaseBean
import com.leifu.commonlib.model.net.response.BaseResponseBean
import com.leifu.commonlib.model.net.rx.FlowableSubscriberManager
import com.leifu.commonlib.model.net.rx.RxManage

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class TPresenter : BaseRxPresenter<TContract.View>(), TContract.Presenter {

    override fun getObjectData() {
        addSubscription(
            RetrofitManager.apiService.getTData()
                .compose(RxManage.rxSchedulerFlowableHelper())
                .compose(RxManage.handleFlowableResult<BaseBean>())
                .subscribeWith(
                    object : FlowableSubscriberManager<BaseBean>(mView, true) {
                        override fun onNext(t: BaseBean) {
                            super.onNext(t)
                            this@TPresenter.mView?.showObjectData(t)
                        }
                    })
        )
    }
}