package com.jackh.wandroid.ui.main

import androidx.databinding.ViewDataBinding
import com.jackh.wandroid.ui.BaseFragment

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:31
 * Description:
 */

abstract class BaseHomeFragment<T : ViewDataBinding> : BaseFragment<T>() {

    abstract fun getNavIconResId(): Int

    abstract fun getNavTitleResId(): Int
}