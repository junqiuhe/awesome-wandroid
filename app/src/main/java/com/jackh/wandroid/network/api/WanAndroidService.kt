package com.jackh.wandroid.network.api

import com.jackh.wandroid.base.model.DataResult
import com.jackh.wandroid.base.model.PageList
import com.jackh.wandroid.model.*
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
     * 每日一问
     */
    @GET("article/list/{page}/json")
    fun qrArticleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int = 440
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

    /**
     * 获取公众号列表
     */
    @GET(value = "wxarticle/chapters/json")
    fun getWxNumberList(): Observable<DataResult<List<SystemTreeInfo>>>

    /**
     * 获取微信公众号文章
     *
     * currentPage 从1开始
     */
    @GET(value = "wxarticle/list/{id}/{currentPage}/json")
    fun getWxArticleById(
        @Path(value = "id") id: Int,
        @Path(value = "currentPage") currentPage: Int,
        @Query(value = "k") key: String? = null
    ): Observable<DataResult<PageList<ArticleInfo>>>

    @GET(value = "hotkey/json")
    fun getHotSearchInfo(): Observable<DataResult<List<HotSearchInfo>>>

    /**
     * 搜索, currentPage 从0开始.
     */
    @FormUrlEncoded
    @POST(value = "article/query/{currentPage}/json")
    fun search(
        @Path(value = "currentPage") currentPage: Int,
        @Field(value = "k") key: String = ""
    ): Observable<DataResult<PageList<ArticleInfo>>>

    /**
     * 获取个人积分信息
     */
    @GET(value = "lg/coin/userinfo/json")
    fun getCoinInfo(): Observable<DataResult<CoinInfo>>

}