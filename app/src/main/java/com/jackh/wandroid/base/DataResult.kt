package com.jackh.wandroid.base

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 9:18
 * Description:
 */
data class DataResult<DATA>(

    val data: DATA?,

    val errorCode: Int,

    val errorMsg: String
)