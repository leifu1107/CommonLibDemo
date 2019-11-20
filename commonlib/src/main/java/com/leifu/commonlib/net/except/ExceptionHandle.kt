package com.leifu.commonlib.net.except

import com.google.gson.JsonParseException
import com.leifu.commonlib.base.IBaseView
import com.leifu.commonlib.util.LogUtil
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * desc: 异常处理类
 */

class ExceptionHandle {
    companion object {
        var errorMsg = "请求失败，请稍后重试"

        fun handleException(e: Throwable, mView: IBaseView?): String {
            e.printStackTrace()
            if (e is ApiException) {
                errorMsg = e.msg.toString()
                if (e.msg == "token timeout") {
                    mView?.showReLogin()
                }
            } else if (e is HttpException) {
                //可以处理一些退出登录逻辑,可以自己修改
                when {
                    e.code() == 404 -> mView?.showError()
                    e.code() == 500 -> mView?.showError()
                    e.code() == 401 -> mView?.showError()
                }
                errorMsg = "服务器错误"
            } else if (e is SocketTimeoutException || e is ConnectException || e is UnknownHostException) { //均视为网络错误
                errorMsg = "网络连接异常"
                mView?.showNoNetwork()
            } else if (e is JsonParseException || e is JSONException || e is ParseException) {   //均视为解析错误
                errorMsg = "数据解析异常"
            } else if (e is IllegalArgumentException) {
                errorMsg = "参数错误"
                mView?.showError()
            } else {//未知错误
                errorMsg = "未知错误，可能抛锚了吧~"
            }
            LogUtil.e("e.message---" + e.message + "errorMsg---" + errorMsg)
            mView?.showErrorMsg(errorMsg)
            return errorMsg
        }

    }


}
