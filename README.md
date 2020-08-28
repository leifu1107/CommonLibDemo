# CommonLibDemo
kotlin公共库

#### 使用kotlin+rxjava2+retrofit对MVP架构做了进一步简化,特别是使用泛型实例化减少每次在Activity中Presenter的创建,同时使用反射跳转登录页面

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

#### BaseActivity的封装

```

abstract class BaseActivity : AppCompatActivity(), IBase {

    lateinit var mActivity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取activity
        mActivity = this
        //订阅事件
        EventBus.getDefault().register(this)
        //设置布局
        setContentView(getLayoutId())
        //初始化数据
        initData()
    }


    /**
     *设置中心title和返回监听
     */
    open fun setTitleText(title: String) {
        centerTitle.text = title
        btnBack.setOnClickListener { finish() }
    }

    /**
     * 重新登录
     */
    override fun onLogin() {
//        SpUtil.removeUseData()
        //todo 注意目录级别和包名等 (完整包名)
//        val any = ReflectUtils.reflect(applicationInfo.processName + ".LoginActivity").get<Class<Activity>>()
//        ActivityUtils.startActivity(any)
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEvent(eventBean: EventBean) {
    }
}
```



