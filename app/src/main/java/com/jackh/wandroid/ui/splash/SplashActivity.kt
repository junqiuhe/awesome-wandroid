package com.jackh.wandroid.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.ActivitySplashBinding
import com.jackh.wandroid.ui.BaseActivity
import com.jackh.wandroid.ui.main.MainActivity

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 14:52
 * Description:
 */

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        launchMainActivity()
    }

    private fun launchMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}