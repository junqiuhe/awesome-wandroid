package com.jackh.wandroid.repository

import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jackh.wandroid.base.App
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.utils.getSharePreferences
import com.jackh.wandroid.utils.string

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 16:04
 * Description:
 */

class AccountManager private constructor() {

    private val prefs: SharedPreferences = App.getContext().getSharePreferences()

    private var _userInfo: String by prefs.string("userInfo", "")

    private var mUserInfo: UserInfo? = null

    fun init() {
        val userInfoJson: String = _userInfo

        if (!TextUtils.isEmpty(userInfoJson)) {
            try {
                mUserInfo = GsonBuilder().create().fromJson(userInfoJson, UserInfo::class.java)
            } catch (e: JsonSyntaxException) {
                clearUserInfo()
                e.printStackTrace()
            }
        }
    }

    fun saveUserInfo(userInfo: UserInfo) {
        _userInfo = GsonBuilder().create().toJson(userInfo)
        mUserInfo = userInfo
    }

    fun clearUserInfo() {
        _userInfo = ""
    }

    /**
     * 判断用户是否登录或者注册
     */
    fun sessionIsOpen(): Boolean {
        return mUserInfo != null
    }

    companion object {

        private var mInstance: AccountManager? = null

        fun getInstance(): AccountManager {
            return synchronized(AccountManager::class.java) {
                if (mInstance == null) {
                    mInstance = AccountManager()
                }
                mInstance!!
            }
        }
    }
}