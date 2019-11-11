package com.jackh.wandroid.ui.account

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
import com.jackh.wandroid.viewmodel.account.HistoryViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 16:35
 * Description:
 */
class HistoryFragment : BaseFragment<CommonRvLayoutBinding>() {

    private val viewModel: HistoryViewModel by lazy {
        getViewModel<HistoryViewModel>()
    }

    private lateinit var listDataUIProxy: ListDataUIProxy<IItem>

    override fun getLayoutId(): Int = R.layout.layout_common_recycler_view

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listDataUIProxy = ListDataUIProxy(context!!){
            viewModel.loadData()
        }

        viewDataBinding = listDataUIProxy.onCreateView(inflater, container)
        viewDataBinding.recyclerView.addItemDecoration(context!!.getCommonListDivider())
        return viewDataBinding.root
    }

    override fun initData(savedInstanceState: Bundle?) {
        listDataUIProxy.initData(viewModel, this, ArticleInfoAdapter())
    }
}