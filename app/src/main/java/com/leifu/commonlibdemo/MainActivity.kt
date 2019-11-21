package com.leifu.commonlibdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.leifu.commonlib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        mBtnJump.setOnClickListener {
            //            startActivity(Intent(this, TextActivity::class.java))
            onLogin()
        }
    }

}
