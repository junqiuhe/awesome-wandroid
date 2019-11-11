package com.jackh.wandroid.repository

import android.annotation.SuppressLint
import com.jackh.wandroid.base.model.ViewState
import com.jackh.wandroid.database.WandroidDatabase
import com.jackh.wandroid.database.dao.HistoryDao
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.HistoryInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 17:19
 * Description:
 */

class HistoryRepository private constructor() {

    private val historyDao: HistoryDao by lazy {
        WandroidDatabase.getInstance().historyDao()
    }

    @SuppressLint("CheckResult")
    fun queryHistoryByUserId(userId: Int): Observable<ViewState<MutableList<ArticleInfo>>> {
        return historyDao.queryHistoryByUserId(userId).map { historyList: List<HistoryInfo> ->
            val list: MutableList<ArticleInfo> = mutableListOf()
            for (history in historyList) {
                list.add(history.articleInfo)
            }
            return@map list
        }.map {
            ViewState.success(it)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(ViewState.loading())
            .onErrorReturn { error: Throwable ->
                ViewState.failure(error)
            }
    }

    fun insertHistory(historyInfo: HistoryInfo) {
        val info = historyDao.queryHistory(userId = historyInfo.userId, historyId = historyInfo.id)
        if(info == null){
            historyDao.insert(historyInfo)
        }
    }

    companion object {
        private var instance: HistoryRepository? = null

        fun getInstance(): HistoryRepository {
            return synchronized(HistoryRepository::class) {
                if (instance == null) {
                    instance = HistoryRepository()
                }
                instance!!
            }
        }
    }
}