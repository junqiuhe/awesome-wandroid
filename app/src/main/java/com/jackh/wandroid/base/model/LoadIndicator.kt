package com.jackh.wandroid.base.model

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 10:57
 * Description: 加载指示器
 */
data class LoadIndicator(
    val isLoading: Boolean, //true: 加载中..., false: 加载完成
    val isRefresh: Boolean //true: 强制刷新, false: 加载更多
)