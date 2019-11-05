package com.jackh.wandroid.ui.search.common

import com.jackh.wandroid.ui.search.BaseSearchDefaultFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.search.SearchViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 10:11
 * Description:
 */

class SearchDefaultFragment : BaseSearchDefaultFragment<SearchViewModel>() {

    override fun initViewModel(): SearchViewModel = getViewModel<SearchViewModel>(activity!!)

}
