package com.jackh.wandroid.viewmodel.account

import androidx.databinding.Bindable
import com.jackh.wandroid.BR
import com.jackh.wandroid.repository.UserRepository
import com.jackh.wandroid.viewmodel.ObservableViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:13
 * Description:
 */
class LoginViewModel(
    private val userRepository: UserRepository
) : ObservableViewModel() {

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

    fun login() {
        userName = "hejunqiu"
        password = "123456"
//        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
//            return
//        }
//        userRepository.login(userName!!, password!!)
    }
}