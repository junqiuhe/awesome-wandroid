package com.jackh.wandroid.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.jackh.wandroid.R
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.ui.account.MineFragment
import com.jackh.wandroid.widget.BottomNavItem
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.IllegalArgumentException

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 15:34
 * Description:
 */

class MainFragment : BaseFragment() {

    companion object {

        private val FRAGMENT_TAG = "home_fragment_tag"

        private val PARAMS_POS = "pos"
    }

    private val mFragments: MutableList<Fragment> = mutableListOf()

    private val mNavItems: MutableList<BottomNavItem> = mutableListOf()

    private var mCurrentPos: Int = 0

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PARAMS_POS, mCurrentPos)
    }

    override fun initData(savedInstanceState: Bundle?) {
        for (position: Int in 0..3) {
            val fragment: BaseHomeFragment = getFragmentByPos(position)
            mFragments.add(fragment)
            mNavItems.add(BottomNavItem(fragment.getNavIconResId(), fragment.getNavTitleResId()))
        }

        val currentPos: Int = savedInstanceState?.getInt(PARAMS_POS, 0) ?: 0
        if (savedInstanceState == null) {
            val ts: FragmentTransaction = childFragmentManager.beginTransaction()
            for (pos: Int in 0 until mFragments.size) {
                ts.add(R.id.main_container, mFragments[pos], FRAGMENT_TAG + pos)
            }
            ts.commit()
        }

        nav_layout.addItems(mNavItems)
        nav_layout.setCurrentIndex(currentPos)
        nav_layout.setOnBottomNavListener { view, position ->
            if (position == mCurrentPos) {
                return@setOnBottomNavListener
            }
            showFragment(position)
        }

        showFragment(currentPos)
    }

    private fun showFragment(position: Int) {
        mCurrentPos = position
        val ts: FragmentTransaction = childFragmentManager.beginTransaction()
        for (pos: Int in 0 until mFragments.size) {
            if (pos == mCurrentPos) {
                ts.show(mFragments[pos])
            } else {
                ts.hide(mFragments[pos])
            }
        }
        ts.commit()
    }

    private fun getFragmentByPos(position: Int): BaseHomeFragment {
        var fragment: BaseHomeFragment? =
            childFragmentManager.findFragmentByTag(FRAGMENT_TAG + position) as? BaseHomeFragment
        return fragment ?: when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                ProjectFragment()
            }
            2 -> {
                WXPublishNumberFragment()
            }
            3 -> {
                MineFragment()
            }
            else -> {
                throw IllegalArgumentException("$position argument error.")
            }
        }
    }
}
