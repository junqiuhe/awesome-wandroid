package com.jackh.wandroid.viewmodel.main

import android.annotation.SuppressLint
import com.jackh.wandroid.model.SystemTreeInfo
import com.jackh.wandroid.repository.WxPublishNumRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/1 10:57
 * Description:
 */

class WxPublishNumViewModel(

    private val repository: WxPublishNumRepository

) : BaseViewModel<List<SystemTreeInfo>>() {

    init {
        loadData()
    }

    @SuppressLint("CheckResult")
    fun loadData() {
        repository.getLocalWxNumberList()?.run {
            _data.value = this
        }
        repository.getRemoteWxNumberList().subscribe(doOnNext {
            _data.value = it
            repository.saveWxNumberList(it)
        })
    }
}