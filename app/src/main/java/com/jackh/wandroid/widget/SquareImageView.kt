package com.jackh.wandroid.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import com.jackh.wandroid.R

/**
 * Project Name：KotlinMvp
 * Created by hejunqiu on 2019/8/6 15:15
 * Description:
 */
class SquareImageView : ImageView {

    private var mBaseWidth: Boolean = true

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        val ta: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.SquareImageView)

        mBaseWidth = ta?.getBoolean(R.styleable.SquareImageView_baseWidth, true) ?: true

        ta?.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mBaseWidth) {
            val measuredWidth: Int = MeasureSpec.getSize(widthMeasureSpec)
            setMeasuredDimension(measuredWidth, measuredWidth)
        } else {
            val measureHeight: Int = MeasureSpec.getSize(heightMeasureSpec)
            setMeasuredDimension(measureHeight, measureHeight)
        }
    }
}