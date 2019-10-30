package com.jackh.wandroid.model

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 9:34
 * Description:
 */
data class UserInfo(
    val admin: Boolean,
    val chapterTops: List<String>?,
    val collectIds: List<String>?,
    val email: String,
    val icon: String,
    val id: Int,
    val token: String,
    val type: Int,
    val nickname: String,
    val publicName: String,
    val username: String
)