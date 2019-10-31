package com.jackh.wandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.HomeAdapter
import com.jackh.wandroid.databinding.CommonRvLayoutBinding
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.utils.ListDataUIProxy
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.main.HomeViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:34
 * Description:
 */

class HomeFragment : BaseHomeFragment<CommonRvLayoutBinding>() {

    private lateinit var mAdapter: HomeAdapter

    private val viewModel: HomeViewModel by lazy {
        getViewModel<HomeViewModel>()
    }

    private lateinit var listDataUIProxy: ListDataUIProxy<ArticleInfo>

    override fun getNavIconResId(): Int = R.drawable.home_icon

    override fun getNavTitleResId(): Int = R.string.title_home

    override fun getLayoutId(): Int = R.layout.layout_common_recycler_view

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        listDataUIProxy = ListDataUIProxy(context!!) { isRefresh ->
            viewModel.loadData(isRefresh)
        }

        viewDataBinding = listDataUIProxy.onCreateView(inflater, container)
        return viewDataBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        mAdapter = HomeAdapter()

        listDataUIProxy.initData(viewModel, this, mAdapter)

        viewModel.loadData(true)
    }
}