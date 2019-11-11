package com.jackh.wandroid.viewmodel.account

import com.jackh.wandroid.model.IItem
import com.jackh.wandroid.repository.AccountManager
import com.jackh.wandroid.repository.HistoryRepository
import com.jackh.wandroid.repository.sessionIsOpen
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 16:38
 * Description:
 */
class HistoryViewModel : BaseViewModel<List<IItem>>() {

    init {
        loadData()
    }

    fun loadData() {
        sessionIsOpen { sessionIsOpen ->
            if (sessionIsOpen) {
                AccountManager.getInstance().getUserInfo()?.run {
                    HistoryRepository.getInstance().queryHistoryByUserId(id)
                        .subscribe(doOnNext {
                            _data.value = it
                            _hasMoreData.value = false
                        })
                }
            } else {
                _data.value = mutableListOf()
                _hasMoreData.value = false
            }
        }
    }
}