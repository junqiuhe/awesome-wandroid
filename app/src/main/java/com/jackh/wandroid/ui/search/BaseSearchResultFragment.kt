package com.jackh.wandroid.ui.search

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackh.wandroid.R
import com.jackh.wandroid.adapter.HomeAdapter
import com.jackh.wandroid.databinding.FragmentSearchResultBinding
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.viewmodel.search.BaseSearchViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/5 10:35
 * Description:
 */

abstract class BaseSearchResultFragment<DATA : ArticleInfo,
        VM : BaseSearchViewModel<List<DATA>>> :
    AbsSearchFragment<FragmentSearchResultBinding, VM>() {

    private lateinit var mAdapter: HomeAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_result
    }

    override fun initData(savedInstanceState: Bundle?) {
        mAdapter = HomeAdapter()
        mAdapter.setOnLoadMoreListener({ viewModel.loadMore() }, viewDataBinding.rv)

        viewDataBinding.rv.layoutManager = LinearLayoutManager(context)
        viewDataBinding.rv.adapter = mAdapter

        viewDataBinding.stateView.setOnRetryBtnClickListener {
            viewModel.search()
        }

        viewModel.getLoadIndicator().observe(this, Observer {
            if (it.isRefresh && it.isLoading) {
                viewDataBinding.stateView.showLoading()
            }
        })

        viewModel.getError().observe(this, Observer { error ->
            mAdapter.loadMoreFail()
            switchState(true)
        })

        viewModel.getData().observe(this, Observer { dataList ->
            mAdapter.loadMoreComplete()
            mAdapter.replaceData(dataList)
            switchState(false)
        })

        viewModel.hasMoreData().observe(this, Observer { hasMore ->
            if (!hasMore) mAdapter.loadMoreEnd()
        })

    }

    private fun switchState(loadError: Boolean) {
        if (mAdapter.data.isEmpty()) {
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