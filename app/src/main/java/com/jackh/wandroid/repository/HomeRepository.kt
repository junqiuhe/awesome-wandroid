package com.jackh.wandroid.repository

import android.annotation.SuppressLint
import com.jackh.wandroid.base.model.PageList
import com.jackh.wandroid.base.model.ViewState
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.BannerInfo
import com.jackh.wandroid.network.getWandroidService
import com.jackh.wandroid.utils.HttpResultFunc
import com.jackh.wandroid.utils.loadDataTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/30 9:12
 * Description:
 */

class HomeRepository private constructor() {

    fun getArticleInfoList(currentPage: Int): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().getArticleInfoList(currentPage)
            .compose(loadDataTransformer())
    }

    fun getQRArticleList(currentPage: Int): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().qrArticleList(currentPage)
            .compose(loadDataTransformer())
    }

    @SuppressLint("CheckResult")
    fun zipLatestBannerTopInfo(currentPage: Int = 0): Observable<ViewState<ZipLatestBannerTopInfo>> {
        return Observable.zip(

            getWandroidService().getArticleInfoList(currentPage)
                .map(HttpResultFunc())
                .map {
                    (it as ViewState.Success).data!!
                },

            getWandroidService().getTopArticle()
                .map(HttpResultFunc(false))
                .map {
                    (it as ViewState.Success).data ?: mutableListOf()
                },

            getWandroidService().getBannerInfo()
                .map(HttpResultFunc(false))
                .map {
                    (it as ViewState.Success).data ?: mutableListOf()
                },

            Function3<PageList<ArticleInfo>, List<ArticleInfo>, List<BannerInfo>, ZipLatestBannerTopInfo> { t1, t2, t3 ->
                ZipLatestBannerTopInfo(t1, t2, t3)
            }

        ).compose(transformer())
    }

    private fun transformer(): ObservableTransformer<ZipLatestBannerTopInfo, ViewState<ZipLatestBannerTopInfo>> {
        return ObservableTransformer { upstream ->
            upstream.map {
                ViewState.success(it)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(ViewState.loading())
                .onErrorReturn { error: Throwable ->
                    ViewState.failure(error)
                }
        }
    }

    companion object {

        private var mRepository: HomeRepository? = null

        fun getInstance(): HomeRepository {
            return synchronized(HomeRepository::class.java) {
                if (mRepository == null) {
                    mRepository = HomeRepository()
                }
                mRepository!!
            }
        }
    }
}

data class ZipLatestBannerTopInfo(
    val articleList: PageList<ArticleInfo>,
    val topArticleList: List<ArticleInfo>?,
    val bannerList: List<BannerInfo>?
)