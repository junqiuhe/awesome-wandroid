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
import io.reactivex.functions.BiFunction

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/30 9:12
 * Description:
 */

class HomeRepository private constructor() {

    fun getArticleInfoList(currentPage: Int): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().getArticleInfoList(currentPage)
            .map(HttpResultFunc())
            .compose(loadDataTransformer())
    }

    @SuppressLint("CheckResult")
    fun zipArticleAndBannerInfo(currentPage: Int = 0): Observable<ViewState<ZipArticleAndBannerInfo>> {
        return Observable.zip(

            getWandroidService().getArticleInfoList(currentPage).map(HttpResultFunc()),

            getWandroidService().getBannerInfo().map(HttpResultFunc(false)),

            BiFunction<PageList<ArticleInfo>, List<BannerInfo>, ZipArticleAndBannerInfo> { t1, t2 ->
                ZipArticleAndBannerInfo(t1, t2)
            }
        ).compose(loadDataTransformer())
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

data class ZipArticleAndBannerInfo(
    val articleList: PageList<ArticleInfo>,
    val bannerList: List<BannerInfo>?
)