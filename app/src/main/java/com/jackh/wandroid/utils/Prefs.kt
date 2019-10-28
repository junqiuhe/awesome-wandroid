package com.jackh.wandroid.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 11:10
 * Description:
 */
private inline fun <T> SharedPreferences.delegate(
    key: String,
    defaultValue: T,
    crossinline getter: SharedPreferences.(String, T) -> T,
    crossinline setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor

): ReadWriteProperty<Any, T> = object : ReadWriteProperty<Any, T>{

    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        getter(key, defaultValue)!!

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) =
        edit().setter(key, value).apply()
}

fun SharedPreferences.int(key: String, defValue: Int = 0): ReadWriteProperty<Any, Int> {
    return delegate(key, defValue, SharedPreferences::getInt, SharedPreferences.Editor::putInt)
}

fun SharedPreferences.long(key: String, defValue: Long = 0): ReadWriteProperty<Any, Long> {
    return delegate(key, defValue, SharedPreferences::getLong, SharedPreferences.Editor::putLong)
}

fun SharedPreferences.float(key: String, defValue: Float = 0f): ReadWriteProperty<Any, Float> {
    return delegate(key, defValue, SharedPreferences::getFloat, SharedPreferences.Editor::putFloat)
}

fun SharedPreferences.boolean(key: String, defValue: Boolean = false): ReadWriteProperty<Any, Boolean> {
    return delegate(key, defValue, SharedPreferences::getBoolean, SharedPreferences.Editor::putBoolean)
}

fun SharedPreferences.stringSet(key: String, defValue: Set<String> = emptySet()): ReadWriteProperty<Any, Set<String>> {
    return delegate(key, defValue, SharedPreferences::getStringSet, SharedPreferences.Editor::putStringSet)
}

fun SharedPreferences.string(key: String, defValue: String = ""): ReadWriteProperty<Any, String> {
    return delegate(key, defValue, SharedPreferences::getString, SharedPreferences.Editor::putString)
}

private const val DEFAULT_SP_TAG = "Wandroid_sp"

fun Context.getSharePreferences(): SharedPreferences{
    return getSharedPreferences(DEFAULT_SP_TAG, Context.MODE_PRIVATE)
}