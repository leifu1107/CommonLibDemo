package com.leifu.commonlibdemo

import com.leifu.commonlib.base.BaseMvpActivity
import com.leifu.commonlib.model.net.response.BaseBean

class TextActivity : BaseMvpActivity<TPresenter>(), TContract.View {
    override fun createPresenter(): TPresenter {
        return TPresenter()
    }

    override fun loadData() {
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_text
    }

    override fun initData() {
        mPresenter?.getObjectData()
    }

    override fun showObjectData(bean: BaseBean) {
    }

}
