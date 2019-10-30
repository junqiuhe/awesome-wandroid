package com.jackh.wandroid.base.model

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 17:18
 * Description:
 */
data class PageInfo(

    var currentPage: Int = 0,

    var pageSize: Int = 20,

    var totalPage: Int = 0
) {
    fun resetData() {
        currentPage = 0
        pageSize = 20
        totalPage = 0
    }
}