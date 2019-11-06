package com.jackh.wandroid.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import com.jackh.wandroid.R;

/**
 * Created by hejunqiu on 2019/5/20 11:00
 *
 * Description：自定义标签: 可以设置标签背景、圆角、边框、边框的宽度
 */
public class LabelTextView extends AppCompatTextView {

    private @ColorInt
    int mBackgroundColor;

    private int mCornerSize;

    private boolean mHasStroke = false;

    private float mStrokeWidth;

    public LabelTextView(Context context) {
        this(context, null);
    }

    public LabelTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.LabelTextView, defStyleAttr, 0);

        mBackgroundColor = typedArray.getColor(R.styleable.LabelTextView_label_background_color, Color.TRANSPARENT);

        mCornerSize = typedArray.getDimensionPixelSize(
                R.styleable.LabelTextView_label_corner, 0);

        mHasStroke = typedArray.getBoolean(R.styleable.LabelTextView_label_has_stroke, false);

        mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.LabelTextView_label_stroke_width, 0);

        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    private Paint mPaint;

    private RectF mRectF;

    private Path mPath;

    private Xfermode mXfermode;

    @Override
    public void setBackground(Drawable background) {
    }

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
        super.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 先执行super.onMeasure, 因为需要获取measureWidth, getLayout().getHeight().
         */
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int width = getMeasuredWidth() + (int) mStrokeWidth * 2;

        int height = getLayout().getHeight() + getPaddingTop() + getPaddingBottom() + (int) mStrokeWidth * 2;

        super.onMeasure(widthMode == MeasureSpec.AT_MOST ?
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY) :
                        widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mBackgroundColor);
        mPaint.setStyle(Paint.Style.FILL);

        if(!mHasStroke || mStrokeWidth == 0){

            calculatePath(false);

            canvas.drawPath(mPath, mPaint);

            canvas.save();
            canvas.translate(mStrokeWidth, mStrokeWidth);
            super.onDraw(canvas);
            canvas.restore();

            return;
        }

        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        calculatePath(false);
        canvas.drawPath(mPath, mPaint);

        mPaint.setXfermode(mXfermode);
        mPaint.setColor(Color.TRANSPARENT);

        calculatePath(true);
        canvas.drawPath(mPath, mPaint);

        mPaint.setXfermode(null);

        canvas.save();
        canvas.translate(mStrokeWidth, mStrokeWidth);
        super.onDraw(canvas);
        canvas.restore();

        canvas.restoreToCount(saveCount);
    }

    private void calculatePath(boolean inner){
        if(mRectF == null){
            mRectF = new RectF();
        }
        if(inner){
            mRectF.set(mStrokeWidth, mStrokeWidth, getWidth() - mStrokeWidth, getHeight() - mStrokeWidth);
        }else{
            mRectF.set(0, 0, getWidth(), getHeight());
        }


        if(mPath == null){
            mPath = new Path();
        }
        mPath.reset();
        if(!inner){
            mPath.addRoundRect(mRectF, mCornerSize, mCornerSize, Path.Direction.CW);
            return;
        }

        if(mCornerSize - mStrokeWidth <= 0){
            mPath.addRoundRect(mRectF, mCornerSize, mCornerSize, Path.Direction.CW);
        }else{
            mPath.addRoundRect(mRectF, mCornerSize - mStrokeWidth, mCornerSize - mStrokeWidth, Path.Direction.CW);
        }
    }
}