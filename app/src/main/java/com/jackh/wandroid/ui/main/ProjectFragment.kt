package com.jackh.wandroid.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.CommonFragmentAdapter
import com.jackh.wandroid.databinding.FragmentProjectBinding
import com.jackh.wandroid.model.SystemTreeInfo
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.main.ProjectViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:35
 * Description:
 */

class ProjectFragment : BaseHomeFragment<FragmentProjectBinding>() {

    private val viewModel: ProjectViewModel by lazy {
        getViewModel<ProjectViewModel>()
    }

    override fun getNavIconResId(): Int = R.drawable.project_icon

    override fun getNavTitleResId(): Int = R.string.title_project

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewDataBinding.stateView.setOnRetryBtnClickListener {
            viewModel.getTreeInfo()
        }

        viewDataBinding.projectTab.setupWithViewPager(viewDataBinding.viewPager)

        viewModel.getLoadIndicator().observe(this, Observer {
            if (it.isLoading && viewModel.getData().value.isNullOrEmpty()) {
                viewDataBinding.stateView.showLoading()
            }
        })

        viewModel.getData().observe(this, Observer { treeList: List<SystemTreeInfo>? ->
            if (treeList.isNullOrEmpty()) {
                viewDataBinding.stateView.showEmpty()
            } else {
                viewDataBinding.stateView.showContent()
            }

            treeList?.run {
                attachFragment(this)
            }
        })

        viewModel.getError().observe(this, Observer {
            if (viewModel.getData().value.isNullOrEmpty()) {
                viewDataBinding.stateView.showError()
            }
        })
    }

    private var adapter: CommonFragmentAdapter? = null

    private fun attachFragment(treeList: List<SystemTreeInfo>) {
        if (adapter == null) {
            adapter = CommonFragmentAdapter(
                manager = childFragmentManager,
                behavior = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
            viewDataBinding.viewPager.adapter = adapter
        }

        val fragmentList = mutableListOf<Fragment>()
        val titleList = mutableListOf<String>()
        for (index in treeList.indices) {
            val systemTreeInfo = treeList[index]

            var fragment: Fragment? = findFragmentByPos(index)
            if (fragment == null) {
                fragment = ProjectListFragment.newInstance(systemTreeInfo.id)
            }
            fragmentList.add(fragment)
            titleList.add(systemTreeInfo.name.replace("&amp;", "&"))
        }

        adapter?.run {
            setFragmentList(fragmentList)
            setTitleList(titleList)
        }
    }

    private fun findFragmentByPos(pos: Int): Fragment? {
        return childFragmentManager.findFragmentByTag("android:switcher:${R.id.view_pager}:${pos}")
    }
}
