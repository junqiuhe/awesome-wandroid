package com.jackh.wandroid.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.jackh.wandroid.R
import com.jackh.wandroid.ui.CustomAppBarConfiguration
import com.jackh.wandroid.ui.CustomToolbarOnDestinationChangedListener
import com.jackh.wandroid.widget.CustomNavToolbar
import java.lang.ref.WeakReference

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


fun Context.getCommonListDivider(): RecyclerView.ItemDecoration {
    val itemDecoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
    itemDecoration.setDrawable(
        ResourcesCompat.getDrawable(
            resources,
            R.drawable.list_divider_bg,
            null
        )!!
    )
    return itemDecoration
}

fun setupWithNavController(
    customNavToolbar: CustomNavToolbar,
    navController: NavController,
    configuration: CustomAppBarConfiguration
) {
    navController.addOnDestinationChangedListener(
        CustomToolbarOnDestinationChangedListener(WeakReference(customNavToolbar), configuration)
    )
    customNavToolbar.setOnBackBtnClickListener {
        navController.navigateUp()
    }
}