package com.jackh.wandroid.ui.search.wxarticle_history

import com.jackh.wandroid.ui.search.BaseSearchDefaultFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.search.SearchWxArticleHistoryViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/5 10:37
 * Description:
 */

class SearchWxArticleDefaultFragment :
    BaseSearchDefaultFragment<SearchWxArticleHistoryViewModel>() {

    override fun initViewModel(): SearchWxArticleHistoryViewModel = getViewModel(activity!!)

}