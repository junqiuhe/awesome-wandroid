package com.jackh.wandroid.network.api

import com.jackh.wandroid.base.model.DataResult
import com.jackh.wandroid.base.model.PageList
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.BannerInfo
import com.jackh.wandroid.model.SystemTreeInfo
import com.jackh.wandroid.model.UserInfo
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 10:52
 * Description:
 */
interface WanAndroidService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(value = "user/login")
    fun login(
        @Field(value = "username") userName: String,
        @Field(value = "password") password: String
    ): Observable<DataResult<UserInfo>>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(value = "user/register")
    fun register(
        @Field(value = "username") userName: String,
        @Field(value = "password") password: String,
        @Field(value = "repassword") repassword: String
    ): Observable<DataResult<UserInfo>>

    /**
     * 退出
     */
    @GET(value = "user/logout/json")
    fun logout(): Observable<DataResult<String>>

    /**
     * 获取首页文章列表
     *
     * 页码，拼接在连接中，从0开始。
     */
    @GET(value = "article/list/{currentPage}/json")
    fun getArticleInfoList(
        @Path(value = "currentPage") currentPage: Int
    ): Observable<DataResult<PageList<ArticleInfo>>>

    /**
     * 获取BannerInfo
     */
    @GET(value = "banner/json")
    fun getBannerInfo(): Observable<DataResult<List<BannerInfo>>>

    /**
     * 项目分类
     */
    @GET(value = "project/tree/json")
    fun getProjectTree(): Observable<DataResult<List<SystemTreeInfo>>>

    @GET(value = "project/list/{currentPage}/json")
    fun getProjectListById(
        @Path(value = "currentPage") currentPage: Int,
        @Query("cid") id: Int
    ): Observable<DataResult<PageList<ArticleInfo>>>

    /**
     * 获取置顶文章
     */
    @GET(value = "article/top/json")
    fun getTopArticle(): Observable<DataResult<List<ArticleInfo>>>

    /**
     * 获取最新项目信息.
     * 从0开始.
     */
    @GET(value = "article/listproject/{currentPage}/json")
    fun getLatestProject(
        @Path(value = "currentPage") currentPage: Int
    ): Observable<DataResult<PageList<ArticleInfo>>>
}