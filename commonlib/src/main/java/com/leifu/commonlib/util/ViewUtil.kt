package com.leifu.commonlib.util

import android.view.View

/**
 *创建人:雷富
 *创建时间:2019/9/11 10:37
 *描述:
 */
class ViewUtil {
    companion object {

        /**
         * 重设 view 的宽高
         */
        fun setViewWH(view: View, width: Int, height: Int) {
            val lp = view.layoutParams
            if (lp.height != height || lp.width != width) {
                lp.width = width
                lp.height = height
                view.layoutParams = lp
            }
        }
    }
}