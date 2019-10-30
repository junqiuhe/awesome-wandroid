package com.jackh.wandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:31
 * Description:
 */

abstract class BaseFragment<DataBinding : ViewDataBinding> : Fragment() {

    protected lateinit var viewDataBinding: DataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(),
            container,
            false
        )
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData(savedInstanceState)
    }

    protected open fun initData(savedInstanceState: Bundle?) {
    }

    protected abstract fun getLayoutId(): Int
}