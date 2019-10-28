package com.jackh.wandroid.network.api

import com.jackh.wandroid.base.DataResult
import com.jackh.wandroid.model.UserInfo
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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
}