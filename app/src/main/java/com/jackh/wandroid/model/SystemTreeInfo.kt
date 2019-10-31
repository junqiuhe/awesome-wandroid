package com.jackh.wandroid.model

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/31 10:26
 * Description:
 */
data class SystemTreeInfo(
    val id: Int,
    val name: String,
    val children: List<SystemTreeInfo>?,
    val courseId: Int,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)