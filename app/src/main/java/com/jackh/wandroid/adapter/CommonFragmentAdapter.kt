package com.jackh.wandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.lang.Exception

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/31 16:54
 * Description:
 */

class CommonFragmentAdapter(

    private val titleList: MutableList<String> = mutableListOf(),

    private val fragmentList: MutableList<Fragment> = mutableListOf(),

    manager: FragmentManager,

    behavior: Int = BEHAVIOR_SET_USER_VISIBLE_HINT

) : FragmentPagerAdapter(manager, behavior) {

    fun setFragmentList(fragmentList: List<Fragment>) {
        this.fragmentList.clear()
        this.fragmentList.addAll(fragmentList)

        notifyDataSetChanged()
    }

    fun setTitleList(titleList: List<String>) {
        this.titleList.clear()
        this.titleList.addAll(titleList)

        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return try {
            titleList[position]
        } catch (e: Exception) {
            ""
        }
    }
}