package com.jackh.wandroid.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jackh.wandroid.model.HistoryInfo
import io.reactivex.Observable

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 16:13
 * Description:
 */
@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historyInfo: HistoryInfo)

    @Query(value = "SELECT * FROM history_info WHERE user_id = :userId")
    fun queryHistoryByUserId(userId: Int): Observable<List<HistoryInfo>>

    @Query(value = "SELECT * from history_info WHERE user_id = :userId and id = :historyId" )
    fun queryHistory(userId: Int, historyId: Int): HistoryInfo?
}