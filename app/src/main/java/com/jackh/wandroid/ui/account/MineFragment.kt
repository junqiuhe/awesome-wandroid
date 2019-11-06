package com.jackh.wandroid.ui.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.FragmentMineBinding
import com.jackh.wandroid.model.CoinInfo
import com.jackh.wandroid.model.UserInfo
import com.jackh.wandroid.repository.AccountManager
import com.jackh.wandroid.repository.SessionStatus
import com.jackh.wandroid.ui.main.BaseHomeFragment
import com.jackh.wandroid.utils.getViewModel
import com.jackh.wandroid.viewmodel.account.MineViewModel

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 16:37
 * Description:
 */
class MineFragment : BaseHomeFragment<FragmentMineBinding>() {

    private val viewModel: MineViewModel by lazy {
        getViewModel<MineViewModel>()
    }

    private val accountManager: AccountManager by lazy {
        AccountManager.getInstance()
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun getNavTitleResId(): Int = R.string.title_mine

    override fun getNavIconResId(): Int = R.drawable.mine_icon

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewDataBinding.loginRegisterBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.accountEntranceActivity)
        }

        accountManager.getUserAction().observe(this, Observer { userAction ->
            visibleMineHeader(
                userAction.sessionStatus == SessionStatus.SESSION_OPEN,
                userAction.userInfo
            )

            if (userAction.sessionStatus == SessionStatus.SESSION_OPEN) {
                viewModel.getCoinInfo()
            }
        })

        accountManager.getCoinInfoLiveData()
            .observe(this, Observer { coinInfo: CoinInfo? ->
                if (coinInfo == null) {
                    viewDataBinding.coinTv.text = "..."
                    viewDataBinding.levelTv.text = "..."
                    viewDataBinding.rankTv.text = "..."
                    return@Observer
                }

                coinInfo.run {
                    viewDataBinding.coinTv.text = getString(R.string.user_coin, "$coinCount")
                    viewDataBinding.levelTv.text =
                        getString(R.string.user_level, "${1 + coinCount / 100}")
                    viewDataBinding.rankTv.text = getString(R.string.user_rank, "$rank")
                }
            })

        visibleMineHeader(
            accountManager.sessionIsOpen(),
            accountManager.getUserInfo()
        )

        viewDataBinding.settingItem.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingFragment)
        }
    }

    private fun visibleMineHeader(sessionIsOpened: Boolean, userInfo: UserInfo?) {
        viewDataBinding.wandroidDesContainer.visibility =
            if (sessionIsOpened) View.GONE else View.VISIBLE
        viewDataBinding.userHeaderContainer.visibility =
            if (sessionIsOpened) View.VISIBLE else View.GONE

        userInfo?.run {
            viewDataBinding.userNameTv.text = nickname
        }
    }
}