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
 * Created by hejunqiu on 2019/10/31 10:32
 * Description:
 */
class ProjectRepository private constructor() {

    private var projectTree: String by App.getContext().getSharePreferences().string(
        "project_tree",
        ""
    )

    fun getLocalProjectTree(): List<SystemTreeInfo>? {
        val jsonString = projectTree
        if (TextUtils.isEmpty(jsonString)) {
            return null
        }
        return try{
            GsonBuilder().create().fromJson(jsonString,
                object : TypeToken<List<SystemTreeInfo>>() {}.type
            )
        }catch (e: JsonSyntaxException){
            e.printStackTrace()
            null
        }
    }

    fun saveProjectTree(list: List<SystemTreeInfo>?){
        list?.run {
            projectTree = GsonBuilder().create().toJson(list)
        }
    }

    fun getRemoteProjectTree(): Observable<ViewState<List<SystemTreeInfo>>> {
        return getWandroidService().getProjectTree()
            .compose(loadDataTransformer(checkResultNull = false))
    }


    fun getProjectListById(
        currentPage: Int,
        id: Int
    ): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().getProjectListById(currentPage, id)
            .compose(loadDataTransformer())
    }

    fun getLatestProjectList(currentPage: Int): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().getLatestProject(currentPage)
            .compose(loadDataTransformer())
    }

    companion object {
        private var repository: ProjectRepository? = null

        fun getInstance(): ProjectRepository {
            return synchronized(ProjectRepository::class.java) {
                if (repository == null) {
                    repository = ProjectRepository()
                }
                repository!!
            }
        }
    }

}