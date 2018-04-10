package com.zly.custom_view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zly.custom_view.R;

import static java.lang.Math.toRadians;

/**
 * Created by Administrator on 2018/4/10.
 */
public class Star extends View {
    private final Paint mPaint;
    private int mCirRadius;
    private int mLineColr;
    private int mLineWidth;
    private int mCirColor;
    private int mCirWidth;
    private String TAG = "Star";
    private int mDegree = 0;

    public Star(Context context) {
        this(context, null);
    }

    public Star(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //obtain the attribute
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.star);
        mLineColr = typedArray.getColor(R.styleable.star_line_color, Color.parseColor("#000000"));
        mLineWidth = (int) typedArray.getDimension(R.styleable.star_line_width, 20);
        mCirColor = typedArray.getColor(R.styleable.star_cir_color, Color.parseColor("#000000"));
        mCirWidth = (int) typedArray.getDimension(R.styleable.star_cir_width, 30);
        mCirRadius = (int) typedArray.getDimension(R.styleable.star_cir_radius, 50);
        typedArray.recycle();

        //create the paint
        mPaint = new Paint();
        mPaint.setColor(mCirColor);
        mPaint.setStrokeWidth(mCirWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasureSize(widthMeasureSpec), getMeasureSize(heightMeasureSpec));
    }

    private int getMeasureSize(int measureSpec) {
        int modeSpec = MeasureSpec.getMode(measureSpec);
        int sizeSpec = MeasureSpec.getSize(measureSpec);
        int result;
        if(modeSpec == MeasureSpec.EXACTLY) {
            Log.d(TAG, "zly --> getMeasureSize EXACTLY.");
            result = sizeSpec;
        } else {
            Log.d(TAG, "zly --> getMeasureSize AT_MOST.");
            result = mCirRadius<<1;
            if(modeSpec == MeasureSpec.AT_MOST) {
                result = Math.min(sizeSpec, result);
            }
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mCirRadius, mCirRadius);
        mDegree += 10;
        canvas.drawArc(-mCirRadius, mCirRadius, mCirRadius, -mCirRadius, 90, mDegree, false, mPaint);
        if (mDegree >= 360) {
            canvas.drawLine(-(float)(mCirRadius*Math.sin(toRadians(45))), -(float)(mCirRadius*Math.cos(toRadians(45))), 0, mCirRadius, mPaint);
        }
    }
}
