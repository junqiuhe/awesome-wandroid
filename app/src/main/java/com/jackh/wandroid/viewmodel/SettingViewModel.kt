package com.jackh.wandroid.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.jackh.wandroid.repository.AccountManager
import com.jackh.wandroid.repository.UserRepository

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/6 17:49
 * Description:
 */

class SettingViewModel : BaseViewModel<Any>() {

    val logout: MutableLiveData<Boolean> = MutableLiveData()

    private val repository: UserRepository by lazy {
        UserRepository.getInstance()
    }

    @SuppressLint("CheckResult")
    fun loginOut() {
        repository.logout().subscribe(doOnNext {
            AccountManager.getInstance().clearUserInfo()

            logout.value = true
        })
    }
}