package com.jackh.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jackh.wandroid.base.model.LoadIndicator
import com.jackh.wandroid.base.model.ViewState
import io.reactivex.functions.Consumer

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:12
 * Description:
 */
open class BaseViewModel<DATA> : ViewModel() {

    protected val _loadIndicator: MutableLiveData<LoadIndicator> = MutableLiveData()

    protected val _error: MutableLiveData<Throwable> = MutableLiveData()

    protected val _data: MutableLiveData<DATA> = MutableLiveData()

    protected val _toast: MutableLiveData<Int> = MutableLiveData()

    protected val _hasMoreData: MutableLiveData<Boolean> = MutableLiveData()

    fun getLoadIndicator(): MutableLiveData<LoadIndicator> = _loadIndicator

    fun getError(): MutableLiveData<Throwable> = _error

    fun getData(): MutableLiveData<DATA> = _data

    fun getToast(): MutableLiveData<Int> = _toast

    fun hasMoreData(): MutableLiveData<Boolean> = _hasMoreData

    protected fun <T> doOnNext(

        isRefresh: Boolean = true,

        loading: ((Boolean) -> Unit)? = null,

        failure: ((Boolean, Throwable) -> Unit)? = null,

        success: ((T?) -> Unit)? = null

    ): Consumer<ViewState<T>> {
        return Consumer { viewState ->
            when (viewState) {
                is ViewState.Loading -> {
                    _loadIndicator.value = LoadIndicator(isLoading = true, isRefresh = isRefresh)
                    loading?.invoke(isRefresh)
                }
                is ViewState.Failure -> {
                    _loadIndicator.value = LoadIndicator(isLoading = false, isRefresh = isRefresh)
                    _error.value = viewState.error

                    failure?.invoke(isRefresh, viewState.error)
                }
                is ViewState.Success -> {
                    _loadIndicator.value = LoadIndicator(isLoading = false, isRefresh = isRefresh)

                    success?.invoke(viewState.data)
                }
            }
        }
    }

    /**
     * 是否正在加载...
     */
    protected fun isLoading(): Boolean = _loadIndicator.value != null
            && _loadIndicator.value!!.isLoading

    override fun onCleared() {
    }
}