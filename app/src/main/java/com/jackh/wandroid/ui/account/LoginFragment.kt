package com.jackh.wandroid.ui.account

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentLoginBinding
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.viewmodel.CustomViewModelFactory
import com.jackh.wandroid.viewmodel.account.LoginViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 10:58
 * Description:
 */
class LoginFragment: BaseFragment<FragmentLoginBinding>(){

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(activity!!, CustomViewModelFactory.getCustomViewModelFactory(context!!))
            .get(LoginViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewDataBinding.viewModel = loginViewModel

        viewDataBinding.lifecycleOwner = viewLifecycleOwner
    }
}