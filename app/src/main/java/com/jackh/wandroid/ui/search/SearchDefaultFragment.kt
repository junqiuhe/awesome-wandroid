package com.jackh.wandroid.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentSearchDefaultBinding
import com.jackh.wandroid.model.HotSearchInfo
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.search.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 10:11
 * Description:
 */

class SearchDefaultFragment : BaseFragment<FragmentSearchDefaultBinding>() {

    private val viewModel: SearchViewModel by lazy {
        getViewModel<SearchViewModel>(activity!!)
    }

    private lateinit var hotSearchAdapter: HotSearchAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_default
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewModel.getHotSearchInfo().observe(this, Observer { list ->
            hotSearchAdapter = HotSearchAdapter(context!!, list)

            viewDataBinding.tagContainer.adapter = hotSearchAdapter
            viewDataBinding.tagContainer.setOnTagClickListener { view, position, parent ->
                viewModel.key.value = list[position].name
                true
            }
        })
    }
}

class HotSearchAdapter(

    private val context: Context,

    hotSearchInfo: List<HotSearchInfo>

) : TagAdapter<HotSearchInfo>(hotSearchInfo) {

    override fun getView(parent: FlowLayout?, position: Int, t: HotSearchInfo?): View {
        val textView: TextView = LayoutInflater.from(context)
            .inflate(
                R.layout.item_hot_search_info,
                parent, false
            ) as TextView
        t?.run {
            textView.text = name
        }
        return textView
    }
}