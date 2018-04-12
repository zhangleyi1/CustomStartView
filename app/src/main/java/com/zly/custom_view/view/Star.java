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
    private int num1 = 9;
    private int num2 = 1;
    private int num3 = 1;
    private int num4 = 1;
    private int num5 = 1;

    public Star(Context context) {
        this(context, null);
    }

    public Star(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //obtain the attribute
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.star);
        mLineColr = typedArray.getColor(R.styleable.star_line_color, Color.parseColor("#000000"));
        mLineWidth = (int) typedArray.getDimension(R.styleable.star_line_width, 20);
        mCirColor = typedArray.getColor(R.styleable.star_cir_color, Color.parseColor("#00ff00"));
        mCirWidth = (int) typedArray.getDimension(R.styleable.star_cir_width, 10);
        mCirRadius = (int) typedArray.getDimension(R.styleable.star_cir_radius, 100);
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
            Log.d(TAG, "zly --> getMeasureSize AT_MOST mCirRadius:" + mCirRadius + " sizeSpec:" + sizeSpec);
            result = (mCirRadius<<1) + mCirWidth;
            if(modeSpec == MeasureSpec.AT_MOST) {
                result = Math.min(sizeSpec, result);
            }
        }
        Log.d(TAG, "zly --> result:" + result);
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.d(TAG, "zly --> onLayout left:" + left + " top:" + top + " right" +  right + " bottom:" + bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mCirRadius + mCirWidth/2, mCirRadius + mCirWidth/2);
        canvas.drawArc(-mCirRadius, -mCirRadius, mCirRadius, mCirRadius, 90, mDegree, false, mPaint);
        Log.d(TAG, "zly --> onDraw mDegree:" + mDegree + " num1:" + num1 + " num2:" + num2 + " num3:" + num3);

        if (mDegree == 360) {
//            Log.d(TAG, "zly --> X1:" + -computeX() + " Y1:" + (computeY()+mCirRadius) + " X2:" + (-computeX()/10*num1)
//                + " Y2:" + ((computeY()+mCirRadius)/10*num1));
            canvas.restore();
            canvas.save();
            canvas.translate(mCirRadius + mCirWidth/2, 0);
            canvas.drawLine(-computeX(), computeY()+mCirRadius, -computeX()/10*num1,
                    ((computeY()+mCirRadius)/10*num1), mPaint);
        } else {
            mDegree += 10;
        }

        if (0 == num1) {
            canvas.drawLine(0, 0, computeX()*num2/10, (computeY()+mCirRadius)*num2/10, mPaint);
        } else if (360 == mDegree) {
            num1--;
        }

        canvas.restore();

        if (10 == num2) {
            canvas.save();
            canvas.translate(mCirRadius + computeX() + mCirWidth/2, mCirRadius + computeY() + mCirWidth/2);
            canvas.drawLine(0, 0, -computeX()*2/10*num3, -computeY()*2/10*num3, mPaint);
            canvas.restore();
        } else if (0 == num1) {
            num2++;
        }

        if (10 == num3) {
            canvas.save();
            canvas.translate(mCirRadius-computeX(), mCirRadius-computeY());
            canvas.drawLine(0, 0, computeX()*2/10*num4, 0, mPaint);
            canvas.restore();
        } else if (10 == num2) {
            num3++;
        }

        if (10 == num4) {
            canvas.save();
            canvas.translate(mCirRadius + computeX(), mCirRadius - computeY());
            canvas.drawLine(0, 0, -computeX()*2/10*num5, computeY()*2/10*num5, mPaint);
            canvas.restore();
        } else if (10 == num3) {
            num4++;
        }

        if (num5 < 10 && 10 == num4) {
            num5++;
        }

        if (mDegree <= 360 || num1 >= 0 || num2 <= 10 || num3 <= 10 || num4 <= 10 || num5 <= 10) {
            postInvalidateDelayed(100);
        }
    }

    private float computeX() {
        return (float) (mCirRadius*Math.cos(Math.toRadians(45)));
    }

    private float computeY() {
        return (float) (mCirRadius*Math.sin(Math.toRadians(45)));
    }
}
