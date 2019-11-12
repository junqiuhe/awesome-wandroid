package com.jackh.wandroid.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentArticleDetailBinding
import com.jackh.wandroid.model.ArticleInfo
import com.jackh.wandroid.ui.webview.WebViewFragment

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/12 17:06
 * Description:
 */

class ArticleDetailFragment : BaseFragment<FragmentArticleDetailBinding>() {

    private var mArticleInfo: ArticleInfo? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(true)

        mArticleInfo = arguments?.getParcelable(PARAMS_ARTICLE_INFO)
    }

    override fun getLayoutId(): Int = R.layout.fragment_article_detail

    override fun initData(savedInstanceState: Bundle?) {
        val fragment = childFragmentManager.findFragmentByTag(TAG_DETAIL_ARTICLE)
            ?: WebViewFragment.newInstance(mArticleInfo?.link)

        if (!fragment.isAdded) {
            val ts = childFragmentManager.beginTransaction()
            ts.replace(R.id.article_detail_container, fragment, TAG_DETAIL_ARTICLE)
            ts.commitAllowingStateLoss()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        mArticleInfo?.run {
            val iconResId = if(collect) R.drawable.collection_selected else R.drawable.collection_default
            menu.findItem(R.id.collection_item).setIcon(iconResId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_article_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.collection_item -> {
                Log.d("hejq", "onMenuItem Click")

                mArticleInfo?.collect = false

                activity?.invalidateOptionsMenu()

                true
            }
            else -> {
                false
            }
        }
    }

    companion object {

        private const val TAG_DETAIL_ARTICLE = "detail_article"

        const val PARAMS_ARTICLE_INFO = "article_info"
    }
}