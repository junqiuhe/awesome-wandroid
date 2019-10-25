package com.jackh.wandroid.ui.account

import androidx.lifecycle.ViewModelProviders
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.ActivityAccountEntranceBinding
import com.jackh.wandroid.ui.BaseActivity
import com.jackh.wandroid.viewmodel.CustomViewModelFactory
import com.jackh.wandroid.viewmodel.account.LoginViewModel
import com.jackh.wandroid.viewmodel.account.RegisterViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 14:34
 * Description:
 */

class AccountEntranceActivity : BaseActivity<ActivityAccountEntranceBinding>() {

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, CustomViewModelFactory.getCustomViewModelFactory(this))
            .get(LoginViewModel::class.java)
    }

    private val registerViewModel: RegisterViewModel by lazy {
        ViewModelProviders.of(this, CustomViewModelFactory.getCustomViewModelFactory(this))
            .get(RegisterViewModel::class.java)
    }

    override fun getLayoutId(): Int = R.layout.activity_account_entrance

}