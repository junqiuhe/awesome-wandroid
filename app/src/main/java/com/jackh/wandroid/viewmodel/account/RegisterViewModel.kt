package com.jackh.wandroid.viewmodel.account

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.jackh.wandroid.base.ViewState
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.repository.UserRepository
import com.jackh.wandroid.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:13
 * Description:
 */
class RegisterViewModel(

    private val userRepository: UserRepository

) : BaseViewModel() {

    val userName: MutableLiveData<String> = MutableLiveData()

    val password: MutableLiveData<String> = MutableLiveData()

    val repassword: MutableLiveData<String> = MutableLiveData()

    /**
     * 加载指示器.
     */
    val loadingIndicator: MutableLiveData<Boolean> = MutableLiveData()

    val error: MutableLiveData<Throwable> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()

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
            .map {
                ViewState.success(it.data)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(ViewState.loading())
            .onErrorReturn {
                ViewState.failure(it)
            }.subscribe { state ->
                when (state) {
                    is ViewState.Loading -> applyState(loading = true)
                    is ViewState.Failure -> applyState(loading = false, throwable = state.error)
                    is ViewState.Success -> applyState(loading = false, user = state.data)
                }
            }
    }

    private fun applyState(loading: Boolean, throwable: Throwable? = null, user: UserInfo? = null) {
        loadingIndicator.value = loading

        throwable?.run {
            error.value = this
        }

        user?.let { it ->
            try {
                userRepository.saveUserInfo(it)
                userInfo.value = it
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}