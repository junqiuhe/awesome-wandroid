package com.jackh.wandroid.viewmodel.main

import android.annotation.SuppressLint
import com.jackh.wandroid.base.model.*
import com.jackh.wandroid.model.IItem
import com.jackh.wandroid.repository.HomeRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 10:31
 * Description:
 */
class LatestArticleViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel<List<IItem>>() {

    private val mDataList: MutableList<IItem> = mutableListOf()

    private var mPageInfo: PageInfo = PageInfo(currentPage = 0)

    @SuppressLint("CheckResult")
    private fun refresh() {

        mPageInfo.resetData()

        homeRepository.zipLatestBannerTopInfo(mPageInfo.currentPage)
            .subscribe(doOnNext {
                it?.run {
                    mPageInfo.currentPage = articleList.curPage
                    mPageInfo.totalPage = articleList.pageCount

                    mDataList.clear()

//                    bannerList?.run {
//                        mDataList.add(BannerItem(this))
//                    }
                    topArticleList?.run {
                        for (topArticle in this) {
                            topArticle.isTop = true
                            mDataList.add(topArticle)
                        }
                    }
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