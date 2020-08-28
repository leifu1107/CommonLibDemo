package com.leifu.commonlibdemo

import com.leifu.commonlib.base.IBasePresenter
import com.leifu.commonlib.base.IBaseView
import com.leifu.commonlib.net.response.BaseBean

interface MainContract {

    interface View : IBaseView {
        /**
         * 显示创建应用接口
         */
        fun showData(bean: BaseBean)

    }

    interface Presenter : IBasePresenter {
        /**
         * 获取创建应用接口
         */
        fun getData()
    }
}