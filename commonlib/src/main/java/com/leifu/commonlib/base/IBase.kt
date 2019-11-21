package com.leifu.commonlib.base

/**
 *创建人:雷富
 *创建时间:2019/6/5 15:17
 *描述:
 */
interface IBase {
    /**
     *  加载布局
     */
    fun getLayoutId(): Int

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 显示重新登录
     */
    fun onLogin()

}