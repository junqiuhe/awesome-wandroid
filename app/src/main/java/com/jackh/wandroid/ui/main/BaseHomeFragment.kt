package com.jackh.wandroid.ui.main

import com.jackh.wandroid.ui.BaseFragment

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:31
 * Description:
 */

abstract class BaseHomeFragment : BaseFragment(){

    abstract fun getNavIconResId(): Int

    abstract fun getNavTitleResId(): Int
}