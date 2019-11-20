package com.leifu.commonlibdemo

import com.leifu.commonlib.base.IBasePresenter
import com.leifu.commonlib.base.IBaseView
import com.leifu.commonlib.model.net.response.BaseBean

interface TContract {

    interface View : IBaseView {
        /**
         * 显示创建应用接口
         */
        fun showObjectData(bean: BaseBean)

    }

    interface Presenter : IBasePresenter {
        /**
         * 获取创建应用接口
         */
        fun getObjectData()
    }
}