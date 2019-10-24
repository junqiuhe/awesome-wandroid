package com.jackh.wandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 9:37
 * Description:
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initData(savedInstanceState)

    }

    protected abstract fun getLayoutId(): Int

    protected open fun initData(savedInstanceState: Bundle?){

    }
}