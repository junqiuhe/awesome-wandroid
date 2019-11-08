package com.jackh.wandroid.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jackh.wandroid.R
import com.jackh.wandroid.model.ArticleInfo

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/8 14:53
 * Description:
 */
class ProjectAdapter : BaseQuickAdapter<ArticleInfo, BaseViewHolder>(R.layout.item_project) {

    private val options: RequestOptions = RequestOptions()
        .error(R.color.color_999)

    override fun convert(helper: BaseViewHolder, item: ArticleInfo?) {
        item?.run {
            if (shareUser.isNotEmpty()) {
                helper.getView<TextView>(R.id.author_tv).text = shareUser

            } else if (author.isNotEmpty()) {
                helper.getView<TextView>(R.id.author_tv).text = author
            }

            helper.getView<TextView>(R.id.title_tv).text = title

            helper.getView<TextView>(R.id.des_tv).text = desc.trim()

            helper.getView<View>(R.id.collection_iv).isSelected = collect

            helper.getView<TextView>(R.id.time_tv).text = niceDate

            Glide.with(helper.itemView.context)
                .load(getResource(helper.itemView.context, mData.indexOf(this) % 13))
                .apply(options)
                .into(helper.getView(R.id.project_icon_iv))
        }
    }

    private fun getResource(context: Context, index: Int): Int {
        return context.resources.getIdentifier("picture_$index", "drawable", context.packageName)
    }
}