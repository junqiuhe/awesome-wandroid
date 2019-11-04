package com.jackh.wandroid.viewmodel.search

import android.annotation.SuppressLint
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.jackh.wandroid.base.model.PageInfo
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.HotSearchInfo
import com.jackh.wandroid.repository.SearchRepository
import com.jackh.wandroid.viewmodel.BaseViewModel
import io.reactivex.disposables.Disposable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 11:30
 * Description:
 */

class SearchViewModel(

    private val repository: SearchRepository

) : BaseViewModel<List<ArticleInfo>>() {

    private var _hotSearchInfo: MediatorLiveData<List<HotSearchInfo>>? = null

    fun getHotSearchInfo(): MediatorLiveData<List<HotSearchInfo>> {
        if (_hotSearchInfo == null) {
            val result = MediatorLiveData<List<HotSearchInfo>>()
            result.addSource(repository.hotSearchInfo) {
                result.value = it
            }
            _hotSearchInfo = result
        }
        return _hotSearchInfo!!
    }

    /**
     * 搜索Key.
     */
    val key: MutableLiveData<String> = MutableLiveData()

    private val dataList: MutableList<ArticleInfo> = mutableListOf()

    private val pageInfo: PageInfo = PageInfo(currentPage = 0)

    @SuppressLint("CheckResult")
    fun search() {
        if (key.value.isNullOrEmpty()) {
            return
        }

        pageInfo.resetData()

        mDisposable?.dispose()

        mDisposable = repository.search(pageInfo.currentPage, key = key.value!!, isRefresh = true)
            .subscribe(doOnNext(failure = { b: Boolean, throwable: Throwable ->
                mDisposable = null

            }, success = { pageList ->
                mDisposable = null

                pageList?.run {
                    pageInfo.currentPage = curPage
                    pageInfo.totalPage = pageCount

                    dataList.clear()
                    datas?.run {
                        dataList.addAll(this)
                    }

                    _data.value = dataList

                    _hasMoreData.value = curPage < pageCount
                }
            }))
    }

    private var mDisposable: Disposable? = null

    @SuppressLint("CheckResult")
    fun loadMore() {
        if (key.value.isNullOrEmpty()) {
            return
        }
        mDisposable = repository.search(pageInfo.currentPage, key = key.value!!, isRefresh = false)
            .subscribe(doOnNext(
                isRefresh = false,
                failure = { b: Boolean, throwable: Throwable ->
                    mDisposable = null

                }, success = { pageList ->
                    mDisposable = null

                    pageList?.run {
                        pageInfo.currentPage = curPage
                        pageInfo.totalPage = pageCount

                        datas?.run {
                            dataList.addAll(this)
                        }
                        _data.value = dataList

                        _hasMoreData.value = curPage < pageCount
                    }
                })
            )
    }
}