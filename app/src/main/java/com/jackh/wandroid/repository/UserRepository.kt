package com.jackh.wandroid.repository

import com.jackh.wandroid.base.model.DataResult
import com.jackh.wandroid.base.model.ViewState
import com.jackh.wandroid.model.CoinInfo
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.network.getWandroidService
import com.jackh.wandroid.utils.HttpResultFunc
import com.jackh.wandroid.utils.loadDataTransformer
import io.reactivex.Observable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:43
 * Description:
 */

class UserRepository private constructor() {
    /**
     * 登录
     */
    fun login(userName: String, password: String): Observable<ViewState<UserInfo>> {
        return getWandroidService().login(userName, password)
            .map(HttpResultFunc())
            .compose(loadDataTransformer())
    }

    /**
     * 注册
     */
    fun register(userName: String, password: String): Observable<ViewState<UserInfo>> {
        return getWandroidService().register(userName, password, password)
            .map(HttpResultFunc())
            .compose(loadDataTransformer())
    }

    /**
     * 退出登录
     */
    fun logout(): Observable<ViewState<String>> {
        return getWandroidService().logout()
            .map(HttpResultFunc(checkResultNull = false))
            .compose(loadDataTransformer())
    }

    /**
     * 获取个人积分信息
     */
    fun getCoinInfo(): Observable<ViewState<CoinInfo>> {
        return getWandroidService().getCoinInfo()
            .map(HttpResultFunc())
            .compose(loadDataTransformer())
    }

    companion object {

        private var userRepository: UserRepository? = null

        fun getInstance(): UserRepository {
            return synchronized(UserRepository::class.java) {
                if (userRepository == null) {
                    userRepository = UserRepository()
                }
                userRepository!!
            }
        }
    }
}