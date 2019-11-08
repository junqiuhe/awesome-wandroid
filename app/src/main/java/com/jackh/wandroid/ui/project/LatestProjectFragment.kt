package com.jackh.wandroid.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.ProjectAdapter
import com.jackh.wandroid.databinding.CommonRvLayoutBinding
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.utils.ListDataUIProxy
import com.jackh.wandroid.utils.getCommonListDivider
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.main.LatestProjectViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/1 10:00
 * Description:
 */
class LatestProjectFragment : BaseFragment<CommonRvLayoutBinding>() {

    private val viewModel: LatestProjectViewModel by lazy {
        getViewModel<LatestProjectViewModel>()
    }

    private lateinit var mAdapter: ProjectAdapter

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
        viewDataBinding.recyclerView.addItemDecoration(context!!.getCommonListDivider())
        return viewDataBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        mAdapter = ProjectAdapter()

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