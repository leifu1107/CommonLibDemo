# CommonLibDemo
kotlin公共库

使用kotlin对MVP架构做了进一步简化,特别是使用泛型实例化减少每次在Activity中Presenter的创建,同时使用反射跳转登录页面

```java

class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.View {

//废弃此方法, BaseActivity中使用泛型实例化了P
//    override fun createPresenter(): MainPresenter {
//        return MainPresenter()
//    }

 override fun getLayoutId(): Int = R.layout.activity_main
}

```
