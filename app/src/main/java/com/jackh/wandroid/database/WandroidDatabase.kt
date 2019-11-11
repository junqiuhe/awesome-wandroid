package com.jackh.wandroid.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jackh.wandroid.base.App
import com.jackh.wandroid.database.dao.CollectionDao
import com.jackh.wandroid.database.dao.HistoryDao
import com.jackh.wandroid.model.CollectionInfo
import com.jackh.wandroid.model.HistoryInfo

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 14:11
 * Description:
 */

@Database(entities = [HistoryInfo::class, CollectionInfo::class], version = 1)
abstract class WandroidDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    abstract fun collectionDao(): CollectionDao

    companion object {

        private var instance: WandroidDatabase? = null

        fun getInstance(): WandroidDatabase {
            return synchronized(WandroidDatabase::class) {
                if (instance == null) {
                    instance = buildDatabase()
                }
                instance!!
            }
        }

        private fun buildDatabase(): WandroidDatabase {
            return Room.databaseBuilder(
                App.getContext(),
                WandroidDatabase::class.java,
                "Wandroid.db"
            ).allowMainThreadQueries().build()
        }
    }
}