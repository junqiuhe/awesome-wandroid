package com.jackh.wandroid.base.model

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 10:02
 * Description:
 */
sealed class ViewState<out T> {

    object Loading : ViewState<Nothing>()

    data class Failure(val error: Throwable) : ViewState<Nothing>()

    data class Success<out T>(val data: T?) : ViewState<T>()

    companion object {
        fun <T> success(result: T?): ViewState<T> =
            Success(result)
        fun <T> loading(): ViewState<T> =
            Loading
        fun <T> failure(error: Throwable): ViewState<T> =
            Failure(error)
    }
}