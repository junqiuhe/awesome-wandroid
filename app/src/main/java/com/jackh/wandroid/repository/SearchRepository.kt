package com.jackh.wandroid.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.jackh.wandroid.base.App
import com.jackh.wandroid.base.model.PageList
import com.jackh.wandroid.base.model.ViewState
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.HotSearchInfo
import com.jackh.wandroid.network.getWandroidService
import com.jackh.wandroid.utils.getSharePreferences
import com.jackh.wandroid.utils.loadDataTransformer
import com.jackh.wandroid.utils.string
import io.reactivex.Observable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 11:11
 * Description:
 */

class SearchRepository private constructor() {

    val hotSearchInfo: MutableLiveData<List<HotSearchInfo>> = MutableLiveData()

    private var _localHotSearchInfo: String by App.getContext().getSharePreferences()
        .string("hotSearchInfo", "")

    @SuppressLint("CheckResult")
    fun preloadHotSearchInfo() {

        getLocalHotSearchInfo()

        getWandroidService().getHotSearchInfo()
            .compose(loadDataTransformer(checkResultNull = false)).subscribe { viewState ->
                when (viewState) {
                    is ViewState.Success -> {
                        saveHotSearchInfo(viewState.data)
                        hotSearchInfo.value = viewState.data
                    }
                    else -> {
                        //do nothing.
                    }
                }
            }
    }

    private fun saveHotSearchInfo(hotSearchInfoList: List<HotSearchInfo>?) {
        hotSearchInfoList?.run {
            _localHotSearchInfo = GsonBuilder().create().toJson(this)
        }
    }

    private fun getLocalHotSearchInfo() {
        val jsonString: String = _localHotSearchInfo
        if (jsonString.isEmpty()) {
            return
        }
        return try {
            hotSearchInfo.value = GsonBuilder().create().fromJson(
                jsonString,
                object : TypeToken<List<HotSearchInfo>>() {}.type
            )
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("CheckResult")
    fun search(
        currentPage: Int,
        key: String = "",
        isRefresh: Boolean
    ): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService()
            .search(currentPage, key)
            .compose(loadDataTransformer())
    }

    companion object {

        private var instance: SearchRepository? = null

        fun getInstance(): SearchRepository {
            return synchronized(SearchRepository::class.java) {
                if (instance == null) {
                    instance = SearchRepository()
                }
                instance!!
            }
        }
    }
}