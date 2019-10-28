package com.jackh.wandroid.ui.account

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.ActivityAccountEntranceBinding
import com.jackh.wandroid.ui.BaseActivity
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.account.LoginViewModel
import com.jackh.wandroid.viewmodel.account.RegisterViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 14:34
 * Description:
 */
class AccountEntranceActivity : BaseActivity<ActivityAccountEntranceBinding>() {

    private val loginViewModel: LoginViewModel by lazy {
        getViewModel<LoginViewModel>()
    }

    private val registerViewModel: RegisterViewModel by lazy {
        getViewModel<RegisterViewModel>()
    }

    override fun getLayoutId(): Int = R.layout.activity_account_entrance

    override fun initData(savedInstanceState: Bundle?) {
        loginViewModel.userInfo.observe(this, Observer { userInfo ->
            onBackPressed()
        })

        registerViewModel.userInfo.observe(this, Observer { userInfo ->
            onBackPressed()
        })
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_account_host_fragment).navigateUp()
}