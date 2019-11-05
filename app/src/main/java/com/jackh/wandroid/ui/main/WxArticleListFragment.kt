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
import com.jackh.wandroid.viewmodel.main.WxArticleListViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/1 11:23
 * Description:
 */

class WxArticleListFragment : BaseFragment<CommonRvLayoutBinding>(){

    private val viewModel: WxArticleListViewModel by lazy {
        getViewModel<WxArticleListViewModel>()
    }

    private lateinit var mAdapter: HomeAdapter

    private lateinit var listDataUIProxy: ListDataUIProxy<ArticleInfo>

    private var wxNumberId: Int = -1

    override fun getLayoutId(): Int = R.layout.layout_common_recycler_view

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listDataUIProxy = ListDataUIProxy(context!!) { isRefresh ->
            viewModel.loadData(isRefresh, wxNumberId)
        }
        viewDataBinding = listDataUIProxy.onCreateView(inflater, container)

        return viewDataBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        wxNumberId = arguments?.getInt(WX_NUMBER_ID, -1) ?: -1

        mAdapter = HomeAdapter()

        listDataUIProxy.initData(viewModel, this, mAdapter)
    }

    private var isFirstLoad: Boolean = true

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            viewModel.loadData(true, wxNumberId)
        }
        isFirstLoad = false
    }

    companion object {

        private const val WX_NUMBER_ID = "wx_number_id"

        fun newInstance(wxNumId: Int): WxArticleListFragment {
            val params = Bundle()
            params.putInt(WX_NUMBER_ID, wxNumId)

            val wxArticleListFragment = WxArticleListFragment()
            wxArticleListFragment.arguments = params
            return wxArticleListFragment
        }
    }
}