package com.jackh.wandroid.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import com.jackh.wandroid.CustomStateBinding
import com.jackh.wandroid.R

/**
 * Project Name：awesome-wandroid
 * Created by hejunqiu on 2019/10/28 17:29
 * Description:
 */

class CustomStateView : FrameLayout {

    private companion object {
        const val STATE_LOADING = 0
        const val STATE_EMPTY = 1
        const val STATE_ERROR = 2
        const val STATE_CONTENT = 3
    }

    private lateinit var customStateBinding: CustomStateBinding

    private var onRetryBtnClickListener: ((View) -> Unit)? = null

    private lateinit var contentView: View

    private var currentState: Int = STATE_CONTENT

    private var errorTextResId: Int = R.string.network_error
    private var errorText: String? = null

    private var errorImageResId: Int = R.drawable.network_error_icon

    private var emptyTextResId: Int = R.string.empty_text
    private var emptyText: String? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initData(context, attrs, defStyleAttr)
    }

    private fun initData(context: Context, attrs: AttributeSet?, defStyleAttr: Int){
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomStateView, defStyleAttr, 0)

        for (index in 0 until typedArray.indexCount) {
            when (val attr = typedArray.getIndex(index)) {
                R.styleable.CustomStateView_errorText -> {

                    errorTextResId = typedArray.getResourceId(attr, R.string.network_error)

                    errorText = typedArray.getString(attr)
                }

                R.styleable.CustomStateView_errorImageResId -> {
                    errorImageResId = typedArray.getResourceId(attr, R.drawable.network_error_icon)
                }

                R.styleable.CustomStateView_emptyText -> {
                    emptyTextResId = typedArray.getResourceId(attr, R.string.empty_text)

                    emptyText = typedArray.getString(attr)
                }
            }
        }

        typedArray.recycle()
    }

    private fun inflateCustomStateView() {
        customStateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_custom_state_view,
            this,
            true
        )
        customStateBinding.retryBtn.setOnClickListener {
            onRetryBtnClickListener?.invoke(it)
        }

        setNetWorkText(errorText)
        setNetWorkText(errorTextResId)

        setNetWorkIcon(errorImageResId)

        setEmptyText(emptyText)
        setEmptyText(emptyTextResId)
    }

    fun setOnRetryBtnClickListener(onRetryBtnClickListener: (View) -> Unit) {
        this.onRetryBtnClickListener = onRetryBtnClickListener
    }

    fun setNetWorkText(text: String?) {
        text?.let {
            customStateBinding.errorTv.text = it
        }
    }
    fun setNetWorkText(@StringRes textResId: Int) {
        customStateBinding.errorTv.setText(textResId)
    }

    fun setNetWorkIcon(@DrawableRes resId: Int) {
        customStateBinding.errorIv.setImageResource(resId)
    }

    fun setEmptyText(text: String?) {
        text?.let {
            customStateBinding.emptyTv.text = it
        }
    }
    fun setEmptyText(@StringRes resId: Int) {
        customStateBinding.emptyTv.setText(resId)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            throw RuntimeException("CustomStateView must have one child")
        }
        contentView = getChildAt(0)

        inflateCustomStateView()
    }

    private fun switchView(state: Int) {
        if (currentState == state) {
            return
        }
        currentState = state

        when (currentState) {

            STATE_LOADING -> {
                customStateBinding.progressView.visibility = View.VISIBLE
                customStateBinding.emptyContainer.visibility = View.INVISIBLE
                customStateBinding.errorContainer.visibility = View.INVISIBLE
                contentView.visibility = View.INVISIBLE
            }

            STATE_CONTENT -> {
                customStateBinding.progressView.visibility = View.INVISIBLE
                customStateBinding.emptyContainer.visibility = View.INVISIBLE
                customStateBinding.errorContainer.visibility = View.INVISIBLE
                contentView.visibility = View.VISIBLE
            }

            STATE_EMPTY -> {
                customStateBinding.progressView.visibility = View.INVISIBLE
                customStateBinding.emptyContainer.visibility = View.VISIBLE
                customStateBinding.errorContainer.visibility = View.INVISIBLE
                contentView.visibility = View.INVISIBLE
            }

            STATE_ERROR -> {
                customStateBinding.progressView.visibility = View.INVISIBLE
                customStateBinding.emptyContainer.visibility = View.INVISIBLE
                customStateBinding.errorContainer.visibility = View.VISIBLE
                contentView.visibility = View.INVISIBLE
            }
        }
    }

    fun showContent() {
        switchView(STATE_CONTENT)
    }

    fun showEmpty(){
        switchView(STATE_EMPTY)
    }

    fun showError(){
        switchView(STATE_ERROR)
    }

    fun showLoading(){
        switchView(STATE_LOADING)
    }
}