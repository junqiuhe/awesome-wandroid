package com.jackh.wandroid.viewmodel.account

import android.annotation.SuppressLint
import com.jackh.wandroid.model.CoinInfo
import com.jackh.wandroid.repository.AccountManager
import com.jackh.wandroid.repository.UserRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/6 16:21
 * Description:
 */

class MineViewModel(

    private val repository: UserRepository

) : BaseViewModel<CoinInfo>() {

    @SuppressLint("CheckResult")
    fun getCoinInfo() {
        repository.getCoinInfo().subscribe(doOnNext {
            AccountManager.getInstance().saveCoinInfo(it!!)
        })
    }
}