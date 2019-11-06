package com.jackh.wandroid.repository

import android.content.SharedPreferences
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jackh.wandroid.base.App
import com.jackh.wandroid.model.CoinInfo
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
    private var _coinInfo: String by prefs.string("coinInfo", "")

    private val _userAction: MutableLiveData<UserAction> = MutableLiveData()

    private val coinInfoLiveData: MutableLiveData<CoinInfo> = MutableLiveData()

    fun getUserAction(): MutableLiveData<UserAction> = _userAction

    fun getCoinInfoLiveData(): MutableLiveData<CoinInfo> = coinInfoLiveData

    fun init() {
        parseCoinInfo()
        initUserInfo()
    }

    private fun initUserInfo() {
        val userInfoJson: String = _userInfo

        if (TextUtils.isEmpty(userInfoJson)) {
            sessionChanged(userAction = UserAction(sessionStatus = SessionStatus.SESSION_CLOSED))
            return
        }

        try {
            val userInfo: UserInfo =
                GsonBuilder().create().fromJson(userInfoJson, UserInfo::class.java)

            sessionChanged(
                userAction = UserAction(
                    userInfo = userInfo,
                    sessionStatus = SessionStatus.SESSION_OPEN
                )
            )
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            sessionChanged(userAction = UserAction(sessionStatus = SessionStatus.SESSION_CLOSED))
        }
    }

    fun saveUserInfo(userInfo: UserInfo) {
        _userInfo = GsonBuilder().create().toJson(userInfo)

        sessionChanged(
            userAction = UserAction(
                userInfo = userInfo,
                sessionStatus = SessionStatus.SESSION_OPEN
            )
        )
    }

    fun clearUserInfo() {
        _userInfo = ""
        _coinInfo = ""

        sessionChanged(userAction = UserAction(sessionStatus = SessionStatus.SESSION_LOGOUT))
        coinInfoLiveData.value = null
    }

    private fun sessionChanged(userAction: UserAction) {
        _userAction.value = userAction
    }

    /**
     * 判断用户是否登录或者注册
     */
    fun sessionIsOpen(): Boolean {
        if (_userAction.value != null && _userAction.value!!.userInfo != null) {
            return true
        }
        return false
    }

    fun getUserInfo(): UserInfo? {
        return _userAction.value?.userInfo
    }

    private fun parseCoinInfo() {
        try {
            val coinInfoJson: String = _coinInfo
            if (!TextUtils.isEmpty(coinInfoJson)) {
                coinInfoLiveData.value =
                    GsonBuilder().create().fromJson(coinInfoJson, CoinInfo::class.java)
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
    }

    fun saveCoinInfo(coinInfo: CoinInfo) {
        _coinInfo = GsonBuilder().create().toJson(coinInfo)
        coinInfoLiveData.value = coinInfo
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

data class UserAction(

    val userInfo: UserInfo? = null,

    val sessionStatus: SessionStatus
)

enum class SessionStatus {
    /**
     * 登录或者注册
     */
    SESSION_OPEN,

    /**
     * 未登录
     */
    SESSION_CLOSED,

    /**
     * 退出登录.
     */
    SESSION_LOGOUT
}