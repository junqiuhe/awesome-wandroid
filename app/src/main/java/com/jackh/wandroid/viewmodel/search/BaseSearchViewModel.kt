package com.jackh.wandroid.viewmodel.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.jackh.wandroid.model.HotSearchInfo
import com.jackh.wandroid.repository.SearchRepository
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/5 10:03
 * Description:
 */
abstract class BaseSearchViewModel<DATA>(

    protected val repository: SearchRepository

) : BaseViewModel<DATA>() {

    private var _hotSearchInfo: MediatorLiveData<List<HotSearchInfo>>? = null

    fun getHotSearchInfo(): MediatorLiveData<List<HotSearchInfo>> {
        if (_hotSearchInfo == null) {
            val result = MediatorLiveData<List<HotSearchInfo>>()
            result.addSource(repository.hotSearchInfo) {
                result.value = it
            }
            _hotSearchInfo = result
        }
        return _hotSearchInfo!!
    }

    /**
     * 搜索Key.
     */
    val key: MutableLiveData<String> = MutableLiveData()

    abstract fun search()

    abstract fun loadMore()

}