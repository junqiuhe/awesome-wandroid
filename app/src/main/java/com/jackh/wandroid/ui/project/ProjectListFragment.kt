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
import com.jackh.wandroid.viewmodel.main.ProjectListViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/31 11:15
 * Description:
 */

class ProjectListFragment : BaseFragment<CommonRvLayoutBinding>() {

    private val viewModel: ProjectListViewModel by lazy {
        getViewModel<ProjectListViewModel>()
    }

    private lateinit var mAdapter: ProjectAdapter

    private lateinit var listDataUIProxy: ListDataUIProxy<ArticleInfo>

    private var projectId: Int? = null

    override fun getLayoutId(): Int = R.layout.layout_common_recycler_view

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listDataUIProxy = ListDataUIProxy(context!!) { isRefresh ->
            viewModel.loadData(isRefresh, projectId!!)
        }

        viewDataBinding = listDataUIProxy.onCreateView(inflater, container)
        viewDataBinding.recyclerView.addItemDecoration(context!!.getCommonListDivider())
        return viewDataBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        projectId = arguments?.getInt(PROJECT_ID, -1) ?: -1

        mAdapter = ProjectAdapter()

        listDataUIProxy.initData(viewModel, this, mAdapter)
    }

    private var isFirstLoad: Boolean = true

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            viewModel.loadData(true, projectId!!)
        }
        isFirstLoad = false
    }

    companion object {

        private const val PROJECT_ID = "project_id"

        fun newInstance(projectId: Int): ProjectListFragment {
            val params = Bundle()
            params.putInt(PROJECT_ID, projectId)

            val projectListFragment = ProjectListFragment()
            projectListFragment.arguments = params
            return projectListFragment
        }
    }
}