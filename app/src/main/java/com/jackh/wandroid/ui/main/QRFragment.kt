package com.jackh.wandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.ArticleInfoAdapter
import com.jackh.wandroid.databinding.CommonRvLayoutBinding
import com.jackh.wandroid.model.IItem
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.utils.ListDataUIProxy
import com.jackh.wandroid.utils.getCommonListDivider
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.main.QRViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/7 16:34
 * Description:
 */
class QRFragment : BaseFragment<CommonRvLayoutBinding>() {

    private val viewModel: QRViewModel by lazy {
        getViewModel<QRViewModel>()
    }

    private lateinit var mAdapter: ArticleInfoAdapter

    private lateinit var listDataUIProxy: ListDataUIProxy<IItem>

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
        mAdapter = ArticleInfoAdapter()

        listDataUIProxy.initData(viewModel, this, mAdapter)
    }

    private var isFirst: Boolean = true

    override fun onResume() {
        super.onResume()
        if (isFirst) {
            viewModel.loadData(true)
        }
        isFirst = false
    }
}