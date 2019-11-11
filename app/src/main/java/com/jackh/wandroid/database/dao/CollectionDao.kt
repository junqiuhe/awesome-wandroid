package com.jackh.wandroid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.jackh.wandroid.model.CollectionInfo

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 16:14
 * Description:
 */
@Dao
interface CollectionDao {

    @Insert
    fun insert(collectionInfo: CollectionInfo)

}