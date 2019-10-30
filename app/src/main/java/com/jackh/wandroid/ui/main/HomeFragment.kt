package com.jackh.wandroid.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.CommonRvLayoutBinding
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.main.HomeViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:34
 * Description:
 */

class HomeFragment : BaseHomeFragment<CommonRvLayoutBinding>() {

    private val mAdapter: HomeAdapter by lazy {
        HomeAdapter()
    }

    private val viewModel: HomeViewModel by lazy {
        getViewModel<HomeViewModel>()
    }

    override fun getNavIconResId(): Int = R.drawable.home_icon

    override fun getNavTitleResId(): Int = R.string.title_home

    override fun getLayoutId(): Int = R.layout.layout_common_recycler_view

    override fun initData(savedInstanceState: Bundle?) {
        viewDataBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        viewDataBinding.recyclerView.adapter = mAdapter
        viewDataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if(lastPosition == mAdapter.itemCount - 1){
                    viewModel.loadData(false)
                }
            }
        })

        viewDataBinding.refreshView.setOnRefreshListener {
            viewModel.loadData(true)
        }

        viewDataBinding.stateView.setOnRetryBtnClickListener { view ->
            viewModel.loadData(true)
        }

        viewModel.getLoadIndicator().observe(this, Observer { loadIndicator ->
            viewDataBinding.refreshView.isRefreshing =
                loadIndicator.isLoading && loadIndicator.isRefresh
        })

        viewModel.getError().observe(this, Observer { error ->
            if (mAdapter.isEmpty()) {
                viewDataBinding.stateView.showError()
            } else {
                viewDataBinding.stateView.showContent()
            }
        })

        viewModel.getData().observe(this, Observer { personList ->
            mAdapter.setDataList(personList)
            if (mAdapter.isEmpty()) {
                viewDataBinding.stateView.showEmpty()
            } else {
                viewDataBinding.stateView.showContent()
            }
        })
    }
}

private class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mDataList: MutableList<ArticleInfo> = mutableListOf()

    fun setDataList(dataList: List<ArticleInfo>) {
        mDataList.clear()
        mDataList.addAll(dataList)

        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean {
        return mDataList.isEmpty()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article_info,
                parent, false)
        return object :  RecyclerView.ViewHolder(itemView) {
        }
    }

    override fun getItemCount(): Int = mDataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleInfo: ArticleInfo = mDataList[position]

        holder.itemView.findViewById<TextView>(R.id.title_tv).text = articleInfo.title
    }
}