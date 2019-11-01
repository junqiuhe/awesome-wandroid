package com.jackh.wandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.HomeAdapter
import com.jackh.wandroid.databinding.CommonRvLayoutBinding
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.utils.ListDataUIProxy
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.main.LatestArticleViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/1 9:51
 * Description: 最新博文
 */
class LatestArticleFragment : BaseFragment<CommonRvLayoutBinding>() {

    private val viewModel: LatestArticleViewModel by lazy {
        getViewModel<LatestArticleViewModel>()
    }

    private lateinit var mAdapter: HomeAdapter

    private lateinit var listDataUIProxy: ListDataUIProxy<ArticleInfo>

    override fun getLayoutId(): Int = R.layout.layout_common_recycler_view

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listDataUIProxy = ListDataUIProxy(context!!) { refresh ->
            viewModel.loadData(refresh)
        }
        viewDataBinding = listDataUIProxy.onCreateView(inflater, container)
        return viewDataBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        mAdapter = HomeAdapter()

        listDataUIProxy.initData(viewModel, this, mAdapter)
    }

    private var isFirst: Boolean = true

    override fun onResume() {
        super.onResume()
        if(isFirst){
            viewModel.loadData(true)
        }
        isFirst = false
    }
}