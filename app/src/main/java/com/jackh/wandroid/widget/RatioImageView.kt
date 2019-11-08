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
class RatioImageView : ImageView {

    private var mBaseWidth: Boolean = true

    // width / height
    private var mRatio: Float = 1.0f

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, -1)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val ta: TypedArray? = context?.obtainStyledAttributes(attrs, R.styleable.RatioImageView)

        mBaseWidth = ta?.getBoolean(R.styleable.RatioImageView_baseWidth, true) ?: true

        mRatio = ta?.getFloat(R.styleable.RatioImageView_ratio, 1.0f) ?: 1.0f

        ta?.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mBaseWidth) {
            val measuredWidth: Int = MeasureSpec.getSize(widthMeasureSpec)

            val height: Int = (measuredWidth / mRatio).toInt()

            setMeasuredDimension(measuredWidth, height)

        } else {
            val measureHeight: Int = MeasureSpec.getSize(heightMeasureSpec)

            val width = (measureHeight * mRatio).toInt()

            setMeasuredDimension(width, measureHeight)
        }
    }
}