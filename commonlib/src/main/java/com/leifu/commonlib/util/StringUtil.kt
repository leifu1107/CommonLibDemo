package com.leifu.commonlib.util

import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.widget.TextView
import com.leifu.commonlib.R
import com.leifu.commonlib.BaseApplication

/**
 *创建人:雷富
 *创建时间:2019/7/3 14:36
 *描述:
 */
class StringUtil {

    companion object {
        /**
         * 数据为null,
         */
        fun isEmpty(string: String?): Boolean {
            return null == string || string.trim().isEmpty()
        }

        /**
         * 数据为null,返回-
         */
        fun isEmptyReturn_(string: String?): String? {
            if (TextUtils.isEmpty(string)) {
                return "--"
            }
            return string
        }

        /**
         * 数据为null,返回" "
         */
        fun isEmptyReturnNull(string: String?): String {
            return if (TextUtils.isEmpty(string)) {
                " "
            } else {
                string.toString()
            }

        }

        /**
         * 数据为null,返回-
         */
        fun isEmptyId(string: String?): String? {
            if (TextUtils.isEmpty(string)) {
                return "未安装"
            }
            return "已安装"
        }

        /**
         * 传递ids,用,分开
         */
        fun getIds(ids: MutableList<String>): String {
            val sb = StringBuilder()
            for (id in ids) {
                if (id.trim().isNotEmpty()) {//去空格
                    if (sb.isNotEmpty()) {
                        sb.append(",")
                    }
                    sb.append(id)
                }
            }
            return sb.toString()
        }


    }

}