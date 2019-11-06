package com.jackh.wandroid.ui.setting

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentSettingBinding
import com.jackh.wandroid.repository.AccountManager
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.SettingViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/6 17:47
 * Description:
 */

class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val viewModel: SettingViewModel by lazy {
        getViewModel<SettingViewModel>()
    }

    override fun getLayoutId(): Int = R.layout.fragment_setting

    override fun initData(savedInstanceState: Bundle?) {

        viewDataBinding.logoutBtn.visibility =
            if (AccountManager.getInstance().sessionIsOpen()) View.VISIBLE else View.GONE

        viewModel.logout.observe(this, Observer {
            findNavController().navigateUp()
        })

        viewDataBinding.logoutBtn.setOnClickListener {
            viewModel.loginOut()
        }
    }
}