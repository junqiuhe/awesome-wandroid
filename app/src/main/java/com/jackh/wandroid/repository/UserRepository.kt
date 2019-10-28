package com.jackh.wandroid.repository

import android.content.Context
import com.jackh.wandroid.base.DataResult
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.network.RetrofitClient
import com.jackh.wandroid.network.api.WanAndroidService
import io.reactivex.Observable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:43
 * Description:
 */

class UserRepository private constructor(
    private val context: Context
) {
    private val service: WanAndroidService = RetrofitClient
        .getInstance()
        .getWanAndroidService()

    /**
     * 登录
     */
    fun login(userName: String, password: String): Observable<DataResult<UserInfo>> {
        return service.login(userName, password)
    }

    /**
     * 注册
     */
    fun register(userName: String, password: String): Observable<DataResult<UserInfo>> {
        return service.register(userName, password, password)
    }

    /**
     * 退出登录
     */
    fun logout(): Observable<DataResult<String>> {
        return service.logout()
    }

    fun saveUserInfo(userInfo: UserInfo) {
        AccountManager.getInstance().saveUserInfo(userInfo)
    }

    companion object {

        private var userRepository: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return synchronized(UserRepository::class.java) {
                if (userRepository == null) {
                    userRepository = UserRepository(context.applicationContext)
                }
                userRepository!!
            }
        }
    }
}