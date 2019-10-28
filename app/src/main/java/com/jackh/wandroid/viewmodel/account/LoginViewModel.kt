package com.jackh.wandroid.viewmodel.account

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.jackh.wandroid.BR
import com.jackh.wandroid.base.DataResult
import com.jackh.wandroid.base.ViewState
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.repository.UserRepository
import com.jackh.wandroid.viewmodel.ObservableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 11:13
 * Description:
 */
class LoginViewModel(
    private val userRepository: UserRepository
) : ObservableViewModel() {

    /**
     * 加载指示器.
     */
    val loadingIndicator: MutableLiveData<Boolean> = MutableLiveData()

    val error: MutableLiveData<Throwable> = MutableLiveData()

    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()

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
            .map { t: DataResult<UserInfo> ->
                ViewState.success(t.data)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(ViewState.loading())
            .onErrorReturn { error: Throwable ->
                ViewState.failure(error)
            }.subscribe { viewState: ViewState<UserInfo> ->
                when (viewState) {
                    is ViewState.Loading ->  applyState(loading = true)
                    is ViewState.Failure -> applyState(loading = false, throwable = viewState.error, user = null)
                    is ViewState.Success -> applyState(loading = false, throwable = null, user = viewState.data)
                }
            }
    }

    private fun applyState(loading: Boolean, throwable: Throwable? = null, user: UserInfo? = null){
        loadingIndicator.value = loading

        throwable?.run {
            error.value = this
        }

        user?.let {it ->
            try{
                userRepository.saveUserInfo(it)
                userInfo.value = it
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}