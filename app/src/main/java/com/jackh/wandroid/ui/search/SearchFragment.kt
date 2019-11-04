package com.jackh.wandroid.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentSearchBinding
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.utils.hideSoftKeyboard
import com.jackh.wandroid.utils.showSoftKeyboard
import com.jackh.wandroid.viewmodel.search.SearchViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 9:26
 * Description:
 */

class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel: SearchViewModel by lazy {
        getViewModel<SearchViewModel>(activity!!)
    }

    override fun getLayoutId(): Int = R.layout.fragment_search

    @SuppressLint("ClickableViewAccessibility")
    override fun initData(savedInstanceState: Bundle?) {
        viewDataBinding.cancelBtn.setOnClickListener {
            findNavController().navigateUp()

            viewModel.key.value = ""
        }

        viewModel.key.observe(this, Observer {
            attachFragment(it)
            viewModel.search()
        })

        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        viewDataBinding.searchContainer.setInterceptListener { view: View, event: MotionEvent? ->
            event?.run {
                if (action == MotionEvent.ACTION_DOWN) {
                    view.hideSoftKeyboard()
                }
            }
        }
        attachFragment()
    }

    private fun attachFragment(searchContent: CharSequence? = null) {
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
                ts.replace(R.id.search_container, fragment, SEARCH_DEFAULT_TAG)
            } else {
                ts.replace(R.id.search_container, fragment, SEARCH_RESULT_TAG)
            }
            ts.commitAllowingStateLoss()
        }
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.searchEt.showSoftKeyboard()
    }

    companion object {

        private const val SEARCH_DEFAULT_TAG = "search_default_tag"

        private const val SEARCH_RESULT_TAG = "search_result_tag"
    }
}