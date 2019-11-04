package com.jackh.wandroid.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.CommonFragmentAdapter
import com.jackh.wandroid.databinding.FragmentHomeBinding

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:34
 * Description:
 */

class HomeFragment : BaseHomeFragment<FragmentHomeBinding>() {

    override fun getNavIconResId(): Int = R.drawable.home_icon

    override fun getNavTitleResId(): Int = R.string.title_home

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initData(savedInstanceState: Bundle?) {
        viewDataBinding.homeTab.setupWithViewPager(viewDataBinding.viewPager)

        viewDataBinding.viewPager.adapter = genAdapter()

        viewDataBinding.searchBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }

    private fun genAdapter(): PagerAdapter {
        val titleList = mutableListOf(
            getString(R.string.home_tab_latest_article_title),
            getString(R.string.home_tab_latest_project_title)
        )
        val fragmentList = mutableListOf<Fragment>()
        for (index in 0 until titleList.size) {
            when (index) {
                0 -> {
                    fragmentList.add(findFragmentByPos(index) ?: LatestArticleFragment())
                }
                1 -> {
                    fragmentList.add(findFragmentByPos(index) ?: LatestProjectFragment())
                }
                else -> {
                    throw IllegalArgumentException("params position is error.")
                }
            }
        }
        return CommonFragmentAdapter(
            titleList, fragmentList, childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
    }

    private fun findFragmentByPos(pos: Int): Fragment? {
        return childFragmentManager.findFragmentByTag("android:switcher:${R.id.view_pager}:${pos}")
    }
}