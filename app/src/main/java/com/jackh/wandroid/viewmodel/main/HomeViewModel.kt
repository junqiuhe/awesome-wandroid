package com.jackh.wandroid.viewmodel.main

import android.annotation.SuppressLint
import com.jackh.wandroid.base.model.*
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.repository.HomeRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 10:31
 * Description:
 */
class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel<List<ArticleInfo>>() {

    private val mDataList: MutableList<ArticleInfo> = mutableListOf()

    private var mPageInfo: PageInfo = PageInfo(currentPage = 0)

    @SuppressLint("CheckResult")
    private fun refresh() {

        mPageInfo.resetData()

        homeRepository.zipArticleAndBannerInfo(mPageInfo.currentPage)
            .subscribe(doOnNext {
                it?.run {
                    mPageInfo.currentPage = articleList.curPage
                    mPageInfo.totalPage = articleList.pageCount

                    mDataList.clear()
                    articleList.datas?.let { it ->
                        mDataList.addAll(it)
                    }
                    _data.value = mDataList
                }
            })
    }

    @SuppressLint("CheckResult")
    private fun loadMore() {
        homeRepository.getArticleInfoList(mPageInfo.currentPage)
            .subscribe(doOnNext(isRefresh = false) {
                it?.run {
                    mPageInfo.currentPage = curPage
                    mPageInfo.totalPage = pageCount

                    datas?.let { it ->
                        mDataList.addAll(it)
                    }

                    _data.value = mDataList

                    _hasMoreData.value = curPage < pageCount
                }
            })
    }

    fun loadData(isRefresh: Boolean) {
        if (isLoading()) {
            return
        }
        if (isRefresh) {
            refresh()
        } else {
            loadMore()
        }
    }
}