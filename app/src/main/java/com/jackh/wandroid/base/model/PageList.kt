package com.jackh.wandroid.base.model

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 15:50
 * Description:
 */
data class PageList<DATA>(

    val curPage: Int,  //当前页

    val datas: List<DATA>?,

    val offset: Int,

    val over: Boolean,

    val pageCount: Int,  //总页数

    val size: Int,  //每页多少条

    val total: Int  //内容的总数.
)