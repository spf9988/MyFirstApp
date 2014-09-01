package com.example.spf.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;

/**
 * TODO: document your custom view class.
 */
public class LockView extends ViewGroup implements OnClickListener
{
    //example
//    private String mExampleString; // TODO: use a default from R.string...
//    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
//    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
//    private Drawable mExampleDrawable;
//
//    private TextPaint mTextPaint;
//    private float mTextWidth;
//    private float mTextHeight;

    //self
    private static final boolean DBG = true;
    private static final String TAG = "SpfLockView";

    private Context m_context;
    private ImageView m_startAdView, m_unlockView;
    private ImageView m_startAdLightView, m_unlockLightView;
    private ImageView m_centerView, m_alphaView;

/*    public LockView(Context context) {
        super(context);
        m_context = context;
        init(null, 0);
    }*/

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_context = context;
        init(attrs, 0);
        initViews(context);
    }

/*    public LockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        m_context = context;
        init(attrs, defStyle);
    }*/

    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (DBG) Log.d(TAG, "onClick");
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
/*
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LockView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.LockView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.LockView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.LockView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.LockView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.LockView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
*/
    }

/*    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }*/

    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    //获取图标，将获取的图标添加入FxLockView，设置图标的可见性
    private void initViews(Context context) {
        m_alphaView = new ImageView(context);
        m_alphaView.setImageResource(R.drawable.centure2);
        setViewsLayout(m_alphaView);
        m_alphaView.setVisibility(View.INVISIBLE);

        m_centerView = new ImageView(context);
        m_centerView.setImageResource(R.drawable.centure1);
        setViewsLayout(m_centerView);
        m_centerView.setVisibility(View.VISIBLE);

        m_unlockView = new ImageView(context);
        m_unlockView.setImageResource(R.drawable.unlock);
        setViewsLayout(m_unlockView);
        m_unlockView.setVisibility(View.VISIBLE);

        m_startAdView = new ImageView(context);
        m_startAdView.setImageResource(R.drawable.dial);
        setViewsLayout(m_startAdView);
        m_startAdView.setVisibility(View.VISIBLE);

        m_startAdLightView= new ImageView(context);
        setLightDrawable(m_startAdLightView);
        setViewsLayout(m_startAdLightView);
        m_startAdLightView.setVisibility(INVISIBLE);

        m_startAdLightView = new ImageView(context);
        setLightDrawable(m_startAdLightView);
        setViewsLayout(m_startAdLightView);
        m_startAdLightView.setVisibility(INVISIBLE);
        
        m_unlockLightView = new ImageView(context);
        setLightDrawable(m_unlockLightView);
        setViewsLayout(m_unlockLightView);
        m_unlockLightView.setVisibility(INVISIBLE);
    }

    private void setViewsLayout(ImageView view) {
        view.setScaleType(ImageView.ScaleType.CENTER);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(view);
    }

    private void setLightDrawable(ImageView img)
    {
        img.setImageResource(R.drawable.light);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

/*        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }*/
    }
/*

    */
/**
     * Gets the example string attribute value.
     * @return The example string attribute value.
     *//*

    public String getExampleString() {
        return mExampleString;
    }

    */
/**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     * @param exampleString The example string attribute value to use.
     *//*

    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    */
/**
     * Gets the example color attribute value.
     * @return The example color attribute value.
     *//*

    public int getExampleColor() {
        return mExampleColor;
    }

    */
/**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     * @param exampleColor The example color attribute value to use.
     *//*

    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    */
/**
     * Gets the example dimension attribute value.
     * @return The example dimension attribute value.
     *//*

    public float getExampleDimension() {
        return mExampleDimension;
    }

    */
/**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     * @param exampleDimension The example dimension attribute value to use.
     *//*

    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    */
/**
     * Gets the example drawable attribute value.
     * @return The example drawable attribute value.
     *//*

    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    */
/**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     * @param exampleDrawable The example drawable attribute value to use.
     *//*

    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
*/
}
