package com.leifu.commonlib.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * 创建人:雷富
 * 创建时间:2019/5/29 14:00
 * 描述:
 */
class VpAdapter(fm: FragmentManager, private val data: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return data[position]
    }

    override fun getCount(): Int {
        return data.size
    }
}

