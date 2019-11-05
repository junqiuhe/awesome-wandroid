package com.jackh.wandroid.ui.search.common

import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.ui.search.BaseSearchResultFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.search.SearchViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 14:07
 * Description:
 */

class SearchResultFragment : BaseSearchResultFragment<ArticleInfo, SearchViewModel>() {

    override fun initViewModel(): SearchViewModel {
        return getViewModel(activity!!)
    }
}