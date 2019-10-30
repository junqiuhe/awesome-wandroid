package com.jackh.wandroid.base

import java.lang.RuntimeException

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/29 18:17
 * Description:
 */
class ApiException(
    val errorCode: String,
    message: String
) : RuntimeException(message)