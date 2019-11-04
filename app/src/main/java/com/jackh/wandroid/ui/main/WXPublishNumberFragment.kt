package com.jackh.wandroid.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.CommonFragmentAdapter
import com.jackh.wandroid.databinding.FragmentWxPublishNumBinding
import com.jackh.wandroid.model.SystemTreeInfo
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.main.WxPublishNumViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:36
 * Description:
 */

class WXPublishNumberFragment : BaseHomeFragment<FragmentWxPublishNumBinding>() {

    private val viewModel: WxPublishNumViewModel by lazy {
        getViewModel<WxPublishNumViewModel>()
    }

    override fun getNavIconResId(): Int = R.drawable.wx_publish_num_icon

    override fun getNavTitleResId(): Int = R.string.title_wx_publish_num

    override fun getLayoutId(): Int = R.layout.fragment_wx_publish_num

    override fun initData(savedInstanceState: Bundle?) {

        viewDataBinding.searchBtn.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }

        viewDataBinding.wxNumberTab.setupWithViewPager(viewDataBinding.viewPager)

        viewModel.getError().observe(this, Observer {
            viewDataBinding.wxNumberTab.visibility =
                if (viewModel.getData().value.isNullOrEmpty()) {
                    viewDataBinding.stateView.showError()
                    View.GONE
                } else {
                    View.VISIBLE
                }
        })

        viewModel.getLoadIndicator().observe(this, Observer {
            viewDataBinding.wxNumberTab.visibility =
                if (viewModel.getData().value.isNullOrEmpty()) {
                    viewDataBinding.stateView.showLoading()
                    View.GONE
                } else {
                    View.VISIBLE
                }
        })

        viewModel.getData().observe(this, Observer {
            viewDataBinding.wxNumberTab.visibility = if (it.isNullOrEmpty()) {
                viewDataBinding.stateView.showEmpty()
                View.GONE
            } else {
                viewDataBinding.stateView.showContent()
                View.VISIBLE
            }

            it?.run {
                attachFragment(this)
            }
        })
    }

    private lateinit var adapter: CommonFragmentAdapter

    private fun attachFragment(treeList: List<SystemTreeInfo>) {
        val fragmentList = mutableListOf<Fragment>()
        val titleList = mutableListOf<String>()
        for (index in treeList.indices) {
            val systemTreeInfo = treeList[index]

            var fragment: Fragment? = findFragmentByPos(index)
            if (fragment == null) {
                fragment = WxArticleListFragment.newInstance(systemTreeInfo.id)
            }
            fragmentList.add(fragment)
            titleList.add(systemTreeInfo.name.replace("&amp;", "&"))
        }

        adapter = CommonFragmentAdapter(
            titleList = titleList,
            fragmentList = fragmentList,
            manager = childFragmentManager,
            behavior = FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewDataBinding.viewPager.adapter = adapter
    }

    private fun findFragmentByPos(pos: Int): Fragment? {
        return childFragmentManager.findFragmentByTag("android:switcher:${R.id.view_pager}:${pos}")
    }
}