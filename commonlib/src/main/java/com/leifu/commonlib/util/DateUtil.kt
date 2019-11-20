package com.leifu.commonlib.util

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 *创建人:雷富
 *创建时间:2019/7/3 18:47
 *描述:
 */
class DateUtil {

    companion object {
        /**
         * 数据为null,返回-
         */
        fun formatTime(long: Long?): String? {
            if (TextUtils.isEmpty(long.toString()) || long?.toInt() == 0) {
                return "--"
            }
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(long?.let { Date(it) })
        }

        /**
         * 格式化07-06月日
         */
        fun timeLongToString(long: Long): String {
            val format = SimpleDateFormat("MM-dd")
            return format.format(Date(long))
        }

        /**
         * 格式化2019-01-01 10:10:10
         */
        fun dateToTime(date: Date): String {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(date)
        }

        /**
         * 获取当前时间戳(13位)
         *
         * @return
         */
        fun getTimeTamp(): Long {
            return Date().time
        }

        fun getTimeTamp(date: Date): Long {
            return date.time
        }

        /**
         * 获取当前日期
         *
         * @return
         */
        fun getCurrentDate(): String {
            val df = SimpleDateFormat("yyyyMMdd")
            return df.format(Date())
        }

        /**
         * 2016-09-03T00:00:00.000+08:00转化
         */
        fun dealDate(oldDateStr: String?): String {
            if (StringUtil.isEmpty(oldDateStr)) {
                return "--"
            }
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date = df.parse(oldDateStr)
            val df1 = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK)
            val date1 = df1.parse(date.toString())
            val df2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return df2.format(date1)
        }

    }
}