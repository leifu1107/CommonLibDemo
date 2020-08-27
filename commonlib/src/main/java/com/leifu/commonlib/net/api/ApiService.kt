package com.leifu.commonlib.net.api

import com.leifu.commonlib.net.response.BaseBean
import io.reactivex.Flowable
import retrofit2.http.GET


/**
 *创建人:雷富
 *创建时间:2019/6/5 17:16
 *描述:
 */
interface ApiService {

    /**
     * 创建应用接口
     */
    @GET("https://wanandroid.com/wxarticle/chapters/json")
    fun getTData(): Flowable<BaseBean>
}