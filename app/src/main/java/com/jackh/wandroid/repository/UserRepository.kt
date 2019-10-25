package com.jackh.wandroid.repository

import android.content.Context

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:43
 * Description:
 */

class UserRepository private constructor(
    private val context: Context
) {

    /**
     * 登录
     */
    fun login(userName: String, password: String) {
    }

    /**
     * 注册
     */
    fun register(userName: String, password: String) {
    }

    /**
     * 退出登录
     */
    fun logout() {

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