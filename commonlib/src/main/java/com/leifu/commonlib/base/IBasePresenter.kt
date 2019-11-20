package com.leifu.commonlib.base

/**
 *创建人:雷富
 *创建时间:2019/6/5 15:17
 *描述:另外一种写法IBasePresenter<V : IBaseView> fun attachView(view: V)
 */
interface IBasePresenter {

    fun attachView(view: IBaseView)

    fun detachView()
}