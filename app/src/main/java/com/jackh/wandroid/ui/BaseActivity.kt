package com.jackh.wandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/24 9:37
 * Description:
 */

abstract class BaseActivity<T: ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())

        initData(savedInstanceState)
    }

    protected abstract fun getLayoutId(): Int

    protected open fun initData(savedInstanceState: Bundle?){
    }
}