package com.jackh.wandroid.viewmodel.main

import android.annotation.SuppressLint
import com.jackh.wandroid.base.model.PageInfo
import com.jackh.wandroid.base.model.PageList
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.repository.WxPublishNumRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/1 10:57
 * Description:
 */
class WxArticleListViewModel(

    private val repository: WxPublishNumRepository

) : BaseViewModel<List<ArticleInfo>>() {

    private var pageInfo: PageInfo = PageInfo(currentPage = 1)

    private val dataList: MutableList<ArticleInfo> = mutableListOf()

    private fun <T : PageList<ArticleInfo>> doSuccess(isRefresh: Boolean): (T?) -> Unit {
        return { pageList ->
            pageList?.run {
                pageInfo.currentPage = curPage + 1
                pageInfo.totalPage = pageCount

                if (isRefresh) {
                    dataList.clear()
                }
                datas?.run {
                    dataList.addAll(this)
                }
                _data.value = dataList

                _hasMoreData.value = curPage < pageCount
            }
        }
    }

    @SuppressLint("CheckResult")
    fun loadData(refresh: Boolean, id: Int) {
        if (isLoading()) {
            return
        }
        if (refresh) {
            pageInfo = PageInfo(currentPage = 1)
        }
        repository.getWxArticleList(id, pageInfo.currentPage)
            .subscribe(
                doOnNext(
                    isRefresh = refresh,
                    success = doSuccess(isRefresh = refresh)
                )
            )
    }
}