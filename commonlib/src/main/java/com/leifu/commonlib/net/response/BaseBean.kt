package com.leifu.commonlib.net.response

import java.io.Serializable

/**
 * 封装返回的数据
 * {"code":200,"msg":"成功!","data":{"appId":"com.chat.peakchao","appkey":"00d91e8e0cca2b76f515926a36db68f5"}}
 */
open class BaseBean : Serializable {
    var errorCode: Int = 0
    var errorMsg: String? = null
}
