package com.jackh.wandroid.repository

import com.jackh.wandroid.base.model.PageList
import com.jackh.wandroid.base.model.ViewState
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.network.getWandroidService
import com.jackh.wandroid.utils.HttpResultFunc
import com.jackh.wandroid.utils.loadDataTransformer
import io.reactivex.Observable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/31 17:10
 * Description:
 */
class ProjectListRepository private constructor() {

    fun getProjectListById(
        currentPage: Int,
        id: Int
    ): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().getProjectListById(currentPage, id)
            .map(HttpResultFunc())
            .compose(
                loadDataTransformer()
            )
    }

    fun getLatestProjectList(currentPage: Int): Observable<ViewState<PageList<ArticleInfo>>> {
        return getWandroidService().getLatestProject(currentPage)
            .map(HttpResultFunc())
            .compose(loadDataTransformer())
    }

    companion object {

        private var instance: ProjectListRepository? = null

        fun getInstance(): ProjectListRepository {
            return synchronized(ProjectListRepository::class.java) {
                if (instance == null) {
                    instance = ProjectListRepository()
                }
                instance!!
            }
        }
    }
}