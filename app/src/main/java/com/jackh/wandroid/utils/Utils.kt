package com.jackh.wandroid.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 11:04
 * Description:
 */
fun View.hideSoftKeyboard() {
    clearFocus()
    val manager: InputMethodManager = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    manager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

fun View.showSoftKeyboard() {
    isFocusable = true
    isFocusableInTouchMode = true
    if (!isFocused) requestFocus()

    val inputMethodManager: InputMethodManager = context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
}