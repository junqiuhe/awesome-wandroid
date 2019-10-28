package com.jackh.wandroid.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.jackh.wandroid.viewmodel.CustomViewModelFactory

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 14:33
 * Description:
 */
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(): T {
    return ViewModelProviders.of(
        this, CustomViewModelFactory.getCustomViewModelFactory(this)
    ).get(T::class.java)
}


inline fun <reified T : ViewModel> Fragment.getViewModel(): T {
    return ViewModelProviders.of(
        this, CustomViewModelFactory.getCustomViewModelFactory(context!!)
    ).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.getViewModel(activity: FragmentActivity): T {
    return ViewModelProviders.of(
        activity, CustomViewModelFactory.getCustomViewModelFactory(activity)
    ).get(T::class.java)
}