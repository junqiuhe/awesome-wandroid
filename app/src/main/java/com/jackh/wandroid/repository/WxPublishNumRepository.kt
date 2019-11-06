package com.jackh.wandroid.repository

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.jackh.wandroid.base.App
import com.jackh.wandroid.base.model.PageList
import com.jackh.wandroid.base.model.ViewState
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.SystemTreeInfo
import com.jackh.wandroid.network.getWandroidService
import com.jackh.wandroid.utils.getSharePreferences
import com.jackh.wandroid.utils.loadDataTransformer
import com.jackh.wandroid.utils.string
import io.reactivex.Observable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/1 11:02
 * Description:
 */

class WxPublishNumRepository private constructor() {

    private var wxNumberList: String by App.getContext().getSharePreferences()
        .string("wx_number_list", "")

    fun getLocalWxNumberList(): List<SystemTreeInfo>? {
        val jsonString = wxNumberList
        if (TextUtils.isEmpty(jsonString)) {
            return null
        }
        return try {
            GsonBuilder().create().fromJson(
                jsonString,
                object : TypeToken<List<SystemTreeInfo>>() {}.type
            )
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }

    fun saveWxNumberList(list: List<SystemTreeInfo>?) {
        list?.run {
            wxNumberList = GsonBuilder().create().toJson(list)
        }
    }

    fun getRemoteWxNumberList(): Observable<ViewState<List<SystemTreeInfo>>> {
        return getWandroidService().getWxNumberList()
            .compose(loadDataTransformer(checkResultNull = false))
    }

    fun getWxArticleList(
        id: Int,
        currentPage: Int,
        key: String? = null
    ): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().getWxArticleById(id, currentPage, key)
            .compose(loadDataTransformer(checkResultNull = false))
    }

    companion object {
        private var instance: WxPublishNumRepository? = null

        fun getInstance(): WxPublishNumRepository? {
            return synchronized(WxPublishNumRepository::class.java) {
                if (instance == null) {
                    instance = WxPublishNumRepository()
                }
                instance!!
            }
        }
    }
}