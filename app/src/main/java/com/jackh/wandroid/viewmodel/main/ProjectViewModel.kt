package com.jackh.wandroid.viewmodel.main

import android.annotation.SuppressLint
import com.jackh.wandroid.model.SystemTreeInfo
import com.jackh.wandroid.repository.ProjectRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/31 10:14
 * Description:
 */
class ProjectViewModel(

    private val repository: ProjectRepository

) : BaseViewModel<List<SystemTreeInfo>>() {

    init {
        getTreeInfo()
    }

    @SuppressLint("CheckResult")
    fun getTreeInfo() {
        repository.getLocalProjectTree()?.run {
            _data.value = this
        }

        repository.getRemoteProjectTree()
            .subscribe(doOnNext { list ->
                _data.value = list
                repository.saveProjectTree(list)
            })
    }
}