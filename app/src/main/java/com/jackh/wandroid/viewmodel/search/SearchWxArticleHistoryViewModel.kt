package com.jackh.wandroid.viewmodel.search

import com.jackh.wandroid.base.model.PageInfo
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.repository.SearchRepository
import com.jackh.wandroid.repository.WxPublishNumRepository
import io.reactivex.disposables.Disposable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/5 10:26
 * Description:
 */

class SearchWxArticleHistoryViewModel(

    searchRepository: SearchRepository,

    private val wxPublishNumRepository: WxPublishNumRepository

) : BaseSearchViewModel<List<ArticleInfo>>(searchRepository){

    private val dataList: MutableList<ArticleInfo> = mutableListOf()

    private var pageInfo: PageInfo = PageInfo(currentPage = 1)

    private var mDisposable: Disposable? = null

    private var _id: Int? = null

    fun setId(id: Int){
        _id = id
    }

    override fun search() {
        if (key.value.isNullOrEmpty() || _id == null) {
            return
        }

        pageInfo = PageInfo(currentPage = 1)

        mDisposable?.dispose()

        mDisposable = wxPublishNumRepository.getWxArticleList(_id!!, pageInfo.currentPage, key = key.value!!)
            .subscribe(doOnNext(failure = { b: Boolean, throwable: Throwable ->
                mDisposable = null

            }, success = { pageList ->
                mDisposable = null

                pageList?.run {
                    pageInfo.currentPage = curPage + 1
                    pageInfo.totalPage = pageCount

                    dataList.clear()
                    datas?.run {
                        dataList.addAll(this)
                    }

                    _data.value = dataList

                    _hasMoreData.value = curPage <= pageCount
                }
            }))
    }

    override fun loadMore() {
        if (key.value.isNullOrEmpty() || _id == null) {
            return
        }

        mDisposable = wxPublishNumRepository.getWxArticleList(_id!!, pageInfo.currentPage, key = key.value!!)
            .subscribe(doOnNext(
                isRefresh = false,
                failure = { b: Boolean, throwable: Throwable ->
                    mDisposable = null

                }, success = { pageList ->
                    mDisposable = null

                    pageList?.run {
                        pageInfo.currentPage = curPage + 1
                        pageInfo.totalPage = pageCount

                        datas?.run {
                            dataList.addAll(this)
                        }
                        _data.value = dataList

                        _hasMoreData.value = curPage <= pageCount
                    }
                })
            )
    }
}