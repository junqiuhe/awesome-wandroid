package com.jackh.wandroid.ui.search

import androidx.databinding.ViewDataBinding
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.viewmodel.search.BaseSearchViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/5 10:11
 * Description:
 */

abstract class AbsSearchFragment<DataBinding : ViewDataBinding, VM : BaseSearchViewModel<*>> : BaseFragment<DataBinding>() {

    protected val viewModel: VM by lazy {
        initViewModel()
    }

    abstract fun initViewModel(): VM
}