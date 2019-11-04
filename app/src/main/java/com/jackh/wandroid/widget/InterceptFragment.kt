package com.jackh.wandroid.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/4 16:34
 * Description:
 */

class InterceptFragment : FrameLayout {

    private var onInterceptListener: ((view: View, ev: MotionEvent?) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setInterceptListener(listener: (view: View, ev: MotionEvent?) -> Unit) {
        onInterceptListener = listener
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if(onInterceptListener != null){
            onInterceptListener!!.invoke(this, ev)
        }
        return super.onInterceptTouchEvent(ev)
    }
}