package com.jackh.wandroid.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 15:41
 * Description:
 */

@Entity(tableName = "history_info")
data class HistoryInfo(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @Embedded
    val articleInfo: ArticleInfo
)