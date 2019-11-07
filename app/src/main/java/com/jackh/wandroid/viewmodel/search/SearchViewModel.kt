package com.jackh.wandroid.viewmodel.search

import com.jackh.wandroid.base.model.PageInfo
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.IItem
import com.jackh.wandroid.repository.SearchRepository
import io.reactivex.disposables.Disposable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 11:30
 * Description:
 */

class SearchViewModel(

    repository: SearchRepository

) : BaseSearchViewModel<List<IItem>>(repository) {

    private val dataList: MutableList<ArticleInfo> = mutableListOf()

    private val pageInfo: PageInfo = PageInfo(currentPage = 0)

    private var mDisposable: Disposable? = null

    override fun search() {
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

    override fun loadMore() {
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