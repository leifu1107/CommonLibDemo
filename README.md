# CommonLibDemo
kotlin公共库

## 使用kotlin+rxjava2+retrofit对MVP架构做了进一步简化,特别是使用泛型实例化减少每次在Activity中Presenter的创建,同时使用反射跳转登录页面

```java

class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.View {

//    Activity中再也不用写这个方法了,废弃此方法, BaseActivity中使用泛型实例化了P,简化了代码
//    override fun createPresenter(): MainPresenter {
//        return MainPresenter()
//    }

    //可以统一把网络请求放这里,当出现错误或者网络异常时,用户手动刷新布局会调用此方法
    override fun loadData() {
        mPresenter?.getData()
    }

    //布局文件
    override fun getLayoutId(): Int = R.layout.activity_main

    //数据的一些初始化
    override fun initData() {
        loadData()
    }

    //网络请求回调
    override fun showData(bean: BaseBean) {

    }

}
```

## BaseActivity的封装





