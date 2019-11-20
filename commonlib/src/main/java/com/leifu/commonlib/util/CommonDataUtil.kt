package com.leifu.commonlib.util

/**
 *创建人:雷富
 *创建时间:2019/6/26 11:35
 *描述:
 */
object CommonDataUtil {
    fun datas(): ArrayList<String> {
        val list = arrayListOf<String>()
        for (i in 0..10) {
            list.add("item$i")
        }
        return list
    }
}