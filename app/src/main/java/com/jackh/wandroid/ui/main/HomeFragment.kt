package com.jackh.wandroid.ui.main

import com.jackh.wandroid.R

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:34
 * Description:
 */

class HomeFragment : BaseHomeFragment(){

    override fun getNavIconResId(): Int = R.drawable.home_icon

    override fun getNavTitleResId(): Int = R.string.title_home

    override fun getLayoutId(): Int = R.layout.fragment_home
}