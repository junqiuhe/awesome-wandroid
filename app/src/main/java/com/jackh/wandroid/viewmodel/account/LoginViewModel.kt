package com.jackh.wandroid.viewmodel.account

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.databinding.Bindable
import com.jackh.wandroid.BR
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.repository.UserRepository
import com.jackh.wandroid.viewmodel.ObservableViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:13
 * Description:
 */
class LoginViewModel(
    private val userRepository: UserRepository
) : ObservableViewModel<UserInfo>() {

    @get:Bindable
    var userName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }

    @get:Bindable
    var password: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    @SuppressLint("CheckResult")
    fun login() {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            return
        }
        userRepository.login(userName!!, password!!)
            .subscribe(doOnNext{
                _data.value = it
            })
    }
}