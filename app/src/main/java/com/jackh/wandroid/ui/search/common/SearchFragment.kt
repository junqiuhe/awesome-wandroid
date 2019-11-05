package com.jackh.wandroid.ui.search.common

import androidx.fragment.app.Fragment
import com.jackh.wandroid.R
import com.jackh.wandroid.ui.search.BaseSearchFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.search.SearchViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 9:26
 * Description:
 */

class SearchFragment : BaseSearchFragment<SearchViewModel>() {

    override fun initViewModel(): SearchViewModel = getViewModel(activity!!)

    override fun attachFragment(searchContent: CharSequence?) {
        val fragment: Fragment = if (searchContent.isNullOrEmpty()) {
            childFragmentManager.findFragmentByTag(SEARCH_DEFAULT_TAG)
                ?: SearchDefaultFragment()
        } else {
            childFragmentManager.findFragmentByTag(SEARCH_RESULT_TAG)
                ?: SearchResultFragment()
        }
        if (!fragment.isAdded) {
            val ts = childFragmentManager.beginTransaction()
            if (searchContent.isNullOrEmpty()) {
                ts.replace(R.id.search_container, fragment,
                    SEARCH_DEFAULT_TAG
                )
            } else {
                ts.replace(R.id.search_container, fragment,
                    SEARCH_RESULT_TAG
                )
            }
            ts.commitAllowingStateLoss()
        }
    }
}