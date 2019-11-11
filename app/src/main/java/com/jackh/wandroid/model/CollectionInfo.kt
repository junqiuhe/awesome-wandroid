package com.jackh.wandroid.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 15:43
 * Description:
 */

@Entity(tableName = "collection_info")
data class CollectionInfo(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @Embedded
    val articleInfo: ArticleInfo
)