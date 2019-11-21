package com.leifu.commonlib.net.except

/**
 *创建人:雷富
 *创建时间:2019/6/5 17:05
 *描述:
 */
class ApiException(var errorCode: Int, var errorMsg: String?) : Exception()