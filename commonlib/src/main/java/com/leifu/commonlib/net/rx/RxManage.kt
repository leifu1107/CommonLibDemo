package com.leifu.commonlib.net.rx


import com.leifu.commonlib.net.except.ApiException
import com.leifu.commonlib.net.response.BaseBean
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * 创建人:雷富
 * 创建时间:2018/7/30 17:38
 * 描述: 2016/8/3.
 * 统一线程处理
 *虽然在Rxjava2中，Flowable是在Observable的基础上优化后的产物，Observable能解决的问题Flowable也都能解决，但是并不代表Flowable可以完全取代Observable,在使用的过程中，并不能抛弃Observable而只用Flowable。
 *由于基于Flowable发射的数据流，以及对数据加工处理的各操作符都添加了背压支持，附加了额外的逻辑，其运行效率要比Observable慢得多。
 *只有在需要处理背压问题时，才需要使用Flowable。
 */
object RxManage {

    /**
     * compose简化线程切换
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerFlowableHelper(): FlowableTransformer<T, T> {
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     *compose判断结果,统一返回结果处理,支持背压
     * @param <T>
     * @return
    </T> */
    fun <T> handleFlowableResult(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable.flatMap(Function<T, Flowable<T>> { bean ->
                val baseBean = bean as BaseBean
                if (baseBean.errorCode == 1) {
                    createFlowable(bean)
                } else {
                    Flowable.error(ApiException(baseBean.errorCode, baseBean.errorMsg))
                }
            })
        }
    }


    /**
     *不通过继承,通过泛型实现实体类
     * @param <T>
     * @return
    </T> */
//    fun <T> handleFlowableResult2(): FlowableTransformer<BaseResponseBean<T>, T> {
//        return FlowableTransformer { flowable ->
//            flowable.flatMap { bean ->
//                if (bean.code == 200) {
//                    createFlowable(bean.data)
//                } else {
//                    Flowable.error(ApiException(bean.code, bean.msg))
//                }
//            }
//        }
//    }


    /**
     * 生成Flowable,支持背压
     * @param <T>
     * @return
    </T> */
    fun <T> createFlowable(t: T): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }
}
