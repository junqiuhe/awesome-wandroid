package com.jackh.wandroid.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.jackh.wandroid.R
import com.jackh.wandroid.databinding.LayoutLabelItemBinding

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/11/6 11:02
 * Description:
 */

class LabelItemView : FrameLayout {

    private val viewDataBinding: LayoutLabelItemBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        viewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_label_item,
            this,
            true
        )

        initData(context, attrs, defStyleAttr)
    }

    private fun initData(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        var hasDivider: Boolean = true
        var hasArrow: Boolean = true

        var labelText: String? = null
        var labelTextResId: Int = -1

        var labelSrc: Int = -1

        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LabelItemView, defStyleAttr, 0)

        for (index in 0 until typedArray.indexCount) {
            when (val attr = typedArray.getIndex(index)) {
                R.styleable.LabelItemView_label_has_divider -> {
                    hasDivider = typedArray.getBoolean(attr, true)
                }

                R.styleable.LabelItemView_label_has_arrow -> {
                    hasArrow = typedArray.getBoolean(attr, true)
                }

                R.styleable.LabelItemView_label_text -> {
                    labelText = typedArray.getString(attr)

                    labelTextResId = typedArray.getResourceId(attr, -1)
                }

                R.styleable.LabelItemView_label_src -> {
                    labelSrc = typedArray.getResourceId(attr, -1)
                }
            }
        }
        typedArray.recycle()

        setHasDivider(hasDivider)
        setHasArrow(hasArrow)

        setLabelText(labelText)

        setLabelText(labelTextResId)

        setLabelSrc(labelSrc)
    }

    fun setHasDivider(hasDivider: Boolean) {
        viewDataBinding.dividerView.visibility = if (hasDivider) View.VISIBLE else View.GONE
    }

    fun setHasArrow(hasArrow: Boolean) {
        viewDataBinding.itemArrowIv.visibility = if (hasArrow) View.VISIBLE else View.GONE
    }

    fun setLabelSrc(srcResId: Int) {
        if (srcResId == -1) {
            return
        }
        viewDataBinding.itemSrcIv.setImageResource(srcResId)
    }

    fun setLabelText(textResId: Int) {
        if (textResId == -1) {
            return
        }
        viewDataBinding.itemContentTv.setText(textResId)
    }

    fun setLabelText(text: String?) {
        text?.run {
            viewDataBinding.itemContentTv.text = this
        }
    }
}