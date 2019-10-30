package com.jackh.wandroid.ui.account

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentRegisterBinding
import com.jackh.wandroid.ui.BaseFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.account.RegisterViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 10:59
 * Description:
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by lazy {
        getViewModel<RegisterViewModel>(activity!!)
    }

    override fun getLayoutId(): Int = R.layout.fragment_register

    override fun initData(savedInstanceState: Bundle?) {

        viewDataBinding.viewModel = viewModel

        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        viewDataBinding.gotoLoginBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}