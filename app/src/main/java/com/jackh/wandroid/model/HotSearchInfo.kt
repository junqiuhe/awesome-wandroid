package com.jackh.wandroid.model

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 11:16
 * Description: 热门搜索TAG.
 */
data class HotSearchInfo(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)