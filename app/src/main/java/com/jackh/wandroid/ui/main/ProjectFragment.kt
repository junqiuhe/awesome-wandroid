package com.jackh.wandroid.ui.main

import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentProjectBinding

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:35
 * Description:
 */

class ProjectFragment : BaseHomeFragment<FragmentProjectBinding>(){

    override fun getNavIconResId(): Int = R.drawable.project_icon

    override fun getNavTitleResId(): Int = R.string.title_project

    override fun getLayoutId(): Int = R.layout.fragment_project
}