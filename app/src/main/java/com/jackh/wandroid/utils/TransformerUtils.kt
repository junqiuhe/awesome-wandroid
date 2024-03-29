package com.jackh.wandroid.utils

import com.jackh.wandroid.base.ApiException
import com.jackh.wandroid.base.model.DataResult
import com.jackh.wandroid.base.model.ViewState
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 17:31
 * Description:
 */

fun <T> loadDataTransformer(checkResultNull: Boolean = true): ObservableTransformer<DataResult<T>, ViewState<T>> {
    return ObservableTransformer { upstream ->
        upstream
            .map(HttpResultFunc(checkResultNull))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(ViewState.loading())
            .onErrorReturn { error: Throwable ->
                ViewState.failure(error)
            }
    }
}

class HttpResultFunc<T>(

    private val checkResultNull: Boolean = true

) : Function<DataResult<T>, ViewState<T>> {

    override fun apply(result: DataResult<T>): ViewState<T> {
        if (result.errorCode != 0) {
            throw ApiException("${result.errorCode}", result.errorMsg)
        }
        if (checkResultNull && result.data == null) {
            throw ApiException("1000", "未知错误")
        }
        return ViewState.success(result.data)
    }
}


