package com.jackh.wandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jackh.wandroid.R

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/15 15:46
 * Description:
 */
class BottomNavLayout : LinearLayout, View.OnClickListener {

    private var mOnItemClickListener: ((view: View, position: Int) -> Unit)? = null

    private val mViewItems: MutableList<View> = ArrayList()

    private var mCurrentIndex: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        orientation = HORIZONTAL
    }

    fun addItems(items: MutableList<BottomNavItem>) {
        clearData()

        for (item in items.withIndex()) {
            val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            params.weight = 1.0f

            val itemView: View =
                LayoutInflater.from(context).inflate(R.layout.layout_bottom_nav, this, false)
            itemView.tag = item.index
            itemView.setOnClickListener(this)

            addView(itemView, params)

            mViewItems.add(itemView)

            val navItem: BottomNavItem = item.value

            val imageView: ImageView = itemView.findViewById(R.id.nav_img_view)
            imageView.setImageResource(navItem.imgResId)

            val textView: TextView = itemView.findViewById(R.id.nav_title_view)
            textView.setText(navItem.titleResId)
        }
        setSelected()
    }

    private fun clearData() {
        removeAllViews()
        mViewItems.clear()
        mCurrentIndex = 0
    }

    fun setCurrentIndex(position: Int) {
        mCurrentIndex = position
        setSelected()
    }

    private fun setSelected() {
        for (item in mViewItems.withIndex()) {
            mViewItems[item.index].isSelected = item.index == mCurrentIndex
        }
    }

    fun setOnBottomNavListener(onItemClickListener: (view: View, position: Int) -> Unit) {
        mOnItemClickListener = onItemClickListener
    }

    override fun onClick(v: View?) {
        v?.run {
            setCurrentIndex(tag as Int)
            mOnItemClickListener?.invoke(this, mCurrentIndex)
        }
    }
}

data class BottomNavItem(val imgResId: Int, val titleResId: Int)