package com.jackh.wandroid.ui.search.wxarticle_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jackh.wandroid.R
import com.jackh.wandroid.model.SystemTreeInfo
import com.jackh.wandroid.ui.search.BaseSearchFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.search.SearchWxArticleHistoryViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/5 10:02
 * Description:
 */
class SearchWxArticleHistoryFragment : BaseSearchFragment<SearchWxArticleHistoryViewModel>() {

    override fun initViewModel(): SearchWxArticleHistoryViewModel = getViewModel(activity!!)

    override fun initData(savedInstanceState: Bundle?) {
        val systemTreeInfo = arguments?.getParcelable<SystemTreeInfo>(PARAMS_SEARCH_WX_NUMBER)
        systemTreeInfo?.run {

            viewDataBinding.searchEt.hint = getString(R.string.search_wx_number_hint, name)

            viewModel.setId(id)

        } ?: throw IllegalArgumentException("Search Wx Article history page params is null.")

        super.initData(savedInstanceState)
    }

    override fun attachFragment(searchContent: CharSequence?) {
        val fragment: Fragment = if (searchContent.isNullOrEmpty()) {
            childFragmentManager.findFragmentByTag(SEARCH_DEFAULT_TAG)
                ?: SearchWxArticleDefaultFragment()
        } else {
            childFragmentManager.findFragmentByTag(SEARCH_RESULT_TAG)
                ?: SearchWxArticleResultFragment()
        }
        if (!fragment.isAdded) {
            val ts = childFragmentManager.beginTransaction()
            if (searchContent.isNullOrEmpty()) {
                ts.replace(
                    R.id.search_container, fragment,
                    SEARCH_DEFAULT_TAG
                )
            } else {
                ts.replace(
                    R.id.search_container, fragment,
                    SEARCH_RESULT_TAG
                )
            }
            ts.commitAllowingStateLoss()
        }
    }

    companion object {
        const val PARAMS_SEARCH_WX_NUMBER = "wx_number_info"
    }
}