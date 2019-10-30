package com.jackh.wandroid.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jackh.wandroid.R
import com.jackh.wandroid.model.ArticleInfo

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/30 16:31
 * Description:
 */

class HomeAdapter : BaseQuickAdapter<ArticleInfo, BaseViewHolder>(R.layout.item_article_info) {

    override fun convert(helper: BaseViewHolder, item: ArticleInfo?) {
        item?.run {
            helper.getView<TextView>(R.id.title_tv).text = title
        }
    }
}