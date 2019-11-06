package com.jackh.wandroid.viewmodel.account

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.repository.AccountManager
import com.jackh.wandroid.repository.UserRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:13
 * Description:
 */
class RegisterViewModel(

    private val userRepository: UserRepository

) : BaseViewModel<UserInfo>() {

    val userName: MutableLiveData<String> = MutableLiveData()

    val password: MutableLiveData<String> = MutableLiveData()

    val repassword: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun register() {
        if (TextUtils.isEmpty(userName.value) ||
            TextUtils.isEmpty(password.value) ||
            TextUtils.isEmpty(repassword.value)
        ) {
            return
        }
        if (password != repassword) {
            return
        }

        userRepository.register(userName = userName.value!!, password = password.value!!)
            .subscribe(doOnNext {
                AccountManager.getInstance().saveUserInfo(it!!)
                _data.value = it
            })
    }
}