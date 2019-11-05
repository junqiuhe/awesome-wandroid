package com.jackh.wandroid.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentSearchBinding
import com.jackh.wandroid.utils.hideSoftKeyboard
import com.jackh.wandroid.utils.showSoftKeyboard
import com.jackh.wandroid.viewmodel.search.BaseSearchViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/5 10:01
 * Description:
 */
abstract class BaseSearchFragment<VM: BaseSearchViewModel<*>> : AbsSearchFragment<FragmentSearchBinding, VM>() {

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

    abstract fun attachFragment(searchContent: CharSequence? = null)

    override fun onResume() {
        super.onResume()
        viewDataBinding.searchEt.showSoftKeyboard()
    }

    companion object {
        const val SEARCH_DEFAULT_TAG = "search_default_tag"

        const val SEARCH_RESULT_TAG = "search_result_tag"
    }
}