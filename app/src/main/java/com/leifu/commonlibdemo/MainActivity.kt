package com.leifu.commonlibdemo

import com.leifu.commonlib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        mBtnJump.setOnClickListener {
//            ActivityUtils.startActivity(TextActivity::class.java)
            onLogin()
        }
    }

}
