package com.jackh.wandroid.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.CommonRvLayoutBinding
import com.jackh.wandroid.viewmodel.BaseViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/30 17:52
 * Description:
 */
class ListDataUIProxy<DATA>(
    private val viewModel: BaseViewModel<List<DATA>>,
    private val adapter: BaseQuickAdapter<DATA, BaseViewHolder>
) {

    private lateinit var viewDataBinding: CommonRvLayoutBinding

    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        viewDataBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(),
            container,
            false
        )
        return viewDataBinding.root
    }

    private fun getLayoutId(): Int = R.layout.layout_common_recycler_view

    fun initData(
        layoutManager: RecyclerView.LayoutManager,
        lifecycleOwner: LifecycleOwner,
        loadData: (Boolean) -> Unit
    ) {
        viewDataBinding.recyclerView.layoutManager = layoutManager
        viewDataBinding.recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener({
            loadData.invoke(false)
        }, viewDataBinding.recyclerView)

        viewDataBinding.refreshView.setOnRefreshListener {
            loadData.invoke(true)
        }
        viewDataBinding.stateView.setOnRetryBtnClickListener { view ->
            loadData.invoke(true)
        }

        viewModel.getLoadIndicator().observe(lifecycleOwner, Observer { loadIndicator ->
            viewDataBinding.refreshView.isRefreshing =
                loadIndicator.isLoading && loadIndicator.isRefresh
        })

        viewModel.getError().observe(lifecycleOwner, Observer { error ->
            adapter.loadMoreFail()
            switchState(true)
        })

        viewModel.getData().observe(lifecycleOwner, Observer { dataList ->
            adapter.loadMoreComplete()
            adapter.replaceData(dataList)
            switchState(false)
        })

        viewModel.hasMoreData().observe(lifecycleOwner, Observer { hasMore ->
            if (!hasMore) adapter.loadMoreEnd()
        })
    }

    private fun switchState(loadError: Boolean) {
        if (adapter.data.isEmpty()) {
            if (loadError) {
                viewDataBinding.stateView.showError()
            } else {
                viewDataBinding.stateView.showEmpty()
            }
        } else {
            viewDataBinding.stateView.showContent()
        }
    }
}