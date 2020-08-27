package com.leifu.commonlibdemo

import com.leifu.commonlib.base.BaseMvpActivity
import com.leifu.commonlib.net.response.BaseBean
import com.leifu.commonlib.util.LogUtil

class TextActivity : BaseMvpActivity<TPresenter>(), TContract.View {
//    override fun createPresenter(): TPresenter {
//        return TPresenter()
//    }

    override fun loadData() {
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_text
    }

    override fun initData() {
        LogUtil.e("请求数据开始")
//        mPresenter?.getObjectData()
    }

    override fun showObjectData(bean: BaseBean) {
    }

}
