package com.jackh.wandroid.ui.account

import com.jackh.wandroid.R
import com.jackh.wandroid.ui.main.BaseHomeFragment

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:37
 * Description:
 */

class MineFragment : BaseHomeFragment(){

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun getNavTitleResId(): Int = R.string.title_mine

    override fun getNavIconResId(): Int = R.drawable.mine_icon

}