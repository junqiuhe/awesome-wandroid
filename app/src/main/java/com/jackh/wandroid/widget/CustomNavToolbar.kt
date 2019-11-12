package com.jackh.wandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.jackh.wandroid.R

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/12 11:46
 * Description:
 */

class CustomNavToolbar : FrameLayout {

    private val _backBtn: View

    private val _titleView: TextView

    private val _toolbar: Toolbar

    private var onBackBtnClickListener: ((v: View) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        removeAllViews()
        LayoutInflater.from(context)
            .inflate(R.layout.layout_custom_header, this, true)

        _backBtn = findViewById(R.id.back_btn)
        _backBtn.setOnClickListener {
            onBackBtnClickListener?.invoke(it)
        }

        _titleView = findViewById(R.id.title_view)

        _toolbar = findViewById(R.id.toolbar)
    }

    fun setOnBackBtnClickListener(listener: (v: View) -> Unit){
        onBackBtnClickListener = listener
    }

    fun getToolbar(): Toolbar = _toolbar

    fun setTitle(title: CharSequence){
        _titleView.text = title
    }

    fun setTitle(resId: Int){
        _titleView.setText(resId)
    }
}