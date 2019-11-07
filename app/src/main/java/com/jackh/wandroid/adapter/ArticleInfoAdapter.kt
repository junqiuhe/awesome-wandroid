package com.jackh.wandroid.adapter

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jackh.wandroid.R
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.model.BannerItem
import com.jackh.wandroid.model.IItem

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/7 10:53
 * Description:
 */
class ArticleInfoAdapter : BaseQuickAdapter<IItem, BaseViewHolder>(null) {

    private val TYPE_ARTICLE_ITEM: Int = -1
    private val TYPE_BANNER_ITEM: Int = -2

    private var _key: String = ""

    fun setKey(key: String) {
        _key = key
    }

    override fun getDefItemViewType(position: Int): Int {
        val item = mData[position]
        return if (item is ArticleInfo) {
            TYPE_ARTICLE_ITEM
        } else {
            TYPE_BANNER_ITEM
        }
    }

    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return if (viewType == TYPE_ARTICLE_ITEM) {
            ArticleItemHolder(
                mLayoutInflater.inflate(
                    R.layout.item_article_info,
                    parent,
                    false
                )
            )
        } else {
            BannerItemHolder(
                mLayoutInflater.inflate(
                    R.layout.item_banner_info,
                    parent,
                    false
                )
            )
        }
    }

    override fun convert(helper: BaseViewHolder, item: IItem?) {
        item?.run {
            if (helper is ArticleItemHolder) {
                helper.updateUI(_key, this as ArticleInfo)
            } else if (helper is BannerItemHolder) {
                helper.updateUI(this as BannerItem)
            }
        }
    }
}

private class ArticleItemHolder(
    itemView: View
) : BaseViewHolder(itemView) {

    fun updateUI(key: String, articleInfo: ArticleInfo) {
        getView<View>(R.id.top_tv).visibility = if (articleInfo.isTop) View.VISIBLE else View.GONE
        getView<View>(R.id.new_tv).visibility = if (articleInfo.fresh) View.VISIBLE else View.GONE

        val tagView: TextView = getView(R.id.tag_tv)
        var tagList = articleInfo.tags
        tagView.visibility = if (tagList.isNullOrEmpty()) View.GONE else View.VISIBLE
        if (!tagList.isNullOrEmpty()) {
            tagView.text = tagList[0].name
        }

        if (articleInfo.shareUser.isNotEmpty()) {
            getView<TextView>(R.id.author_tv).text = articleInfo.shareUser

        } else if (articleInfo.author.isNotEmpty()) {
            getView<TextView>(R.id.author_tv).text = articleInfo.author
        }

        getView<TextView>(R.id.chapter_tv).text =
            articleInfo.superChapterName + " / " + articleInfo.chapterName

        val text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
            articleInfo.title,
            Html.FROM_HTML_MODE_LEGACY
        ) else Html.fromHtml(articleInfo.title)

        getView<TextView>(R.id.title_tv).text = formatText(key, text.toString())

        getView<View>(R.id.collection_icon).isSelected = articleInfo.collect

        getView<TextView>(R.id.time_tv).text = articleInfo.niceDate
    }
}

private fun formatText(key: String, sourceText: String): SpannableStringBuilder {
    val spannableStringBuilder = SpannableStringBuilder()
    spannableStringBuilder.append(sourceText)

    if (key.isEmpty()) {
        return spannableStringBuilder
    }
    val text = sourceText.toUpperCase()
    val start: Int = text.indexOf(key.toUpperCase())
    val end: Int = start + key.length

    if (start < 0) {
        return spannableStringBuilder
    }

    spannableStringBuilder.setSpan(
        ForegroundColorSpan(Color.parseColor("#E64C73")),
        start,
        end,
        Spannable.SPAN_INCLUSIVE_INCLUSIVE
    )

    return spannableStringBuilder
}

private class BannerItemHolder(
    itemView: View
) : BaseViewHolder(itemView) {

    fun updateUI(bannerItem: BannerItem) {
    }
}