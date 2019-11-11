package com.jackh.wandroid.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jackh.wandroid.model.TagInfo

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/11 16:07
 * Description:
 */

class TagConverters {

    @TypeConverter
    fun stringToObject(value: String): List<TagInfo> {
        return Gson().fromJson(value, object : TypeToken<List<TagInfo>>() {
        }.type)
    }

    @TypeConverter
    fun objectToString(list: List<TagInfo>): String {
        return Gson().toJson(list)
    }
}