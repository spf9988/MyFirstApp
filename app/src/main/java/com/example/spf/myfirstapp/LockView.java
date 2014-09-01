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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.MotionEvent;

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

    class ViewDimension
    {
        public int width;
        public int height;
    }
    //self
    private static final boolean DBG = true;
    private static final String TAG = "SpfLockView";

    private Context m_context;
    private ImageView m_startAdView, m_unlockView;
    private ImageView m_startAdLightView, m_unlockLightView;
    private ImageView m_centerView, m_alphaView;

    private AlphaAnimation alpha;
    private boolean m_tracking = false;

    private float m_x, m_y;
    private int m_halfCenterViewWidth, m_halfCenterViewHeight;
    private int m_screenWidth, m_screenHeight, m_quarterScreenWidth;
    private ViewDimension m_alphaViewDimension = new ViewDimension();
    private ViewDimension m_centerViewDimension = new ViewDimension();
    private ViewDimension m_startAdViewDimension = new ViewDimension();
    private ViewDimension m_unlockViewDimension = new ViewDimension();
    private ViewDimension m_startAdLightViewDimension = new ViewDimension();
    private ViewDimension m_unlockLightViewDimension = new ViewDimension();
    private int m_centerViewTop, m_centerViewBottom;
    private int m_alphaViewTop, m_alphaViewBottom;

    //private float m_y;
/*    public LockView(Context context) {
        super(context);
        m_context = context;
        init(null, 0);
    }*/

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_context = context;
        if(DBG) Log.d(TAG, "LockView 1");
        init(attrs, 0);
        initViews(context);
        if(DBG) Log.d(TAG, "LockView 2");

        m_halfCenterViewWidth = m_centerView.getWidth()/2;
        m_halfCenterViewHeight = m_centerView.getHeight()/2;

        onAnimationStart();
    }

/*    public LockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        m_context = context;
        init(attrs, defStyle);
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {    //手指点在中心图标范围区域内
                //if (mCenterViewRect.contains((int) x, (int) y))
                m_tracking = true;
                //stopViewAnimation();
                onAnimationEnd();
                m_alphaView.setVisibility(View.INVISIBLE);
                return true;
            }
            default:
                break;
        }
        if(DBG) Log.d(TAG, "onInterceptTouchEvent()");

        //此处返回false，onClick事件才能监听的到.  需要实验理解ViewGroup文档中此处的return value的说明
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            /*m_tracking为true时，说明中心图标被点击移动
             * 即只有在中心图标被点击移动的情况下，onTouchEvent
             * 事件才会触发。
             */
        if (m_tracking)
        {
            final int action = event.getAction();
            final float nx = event.getX();
            final float ny = event.getY();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (DBG) Log.d(TAG, "MotionEvent.ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                    //setTargetViewVisible(nx, ny);
                    //中心图标移动
                    handleMoveView(nx, ny);
                    break;
                case MotionEvent.ACTION_UP:
                    m_tracking = false;
                    doTriggerEvent(m_x, m_y);
                    resetMoveView();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    m_tracking = false;
                    doTriggerEvent(m_x, m_y);
                    resetMoveView();
                    break;
            }
        }
        if(DBG) Log.d(TAG, "onTouchEvent()");
        return m_tracking || super.onTouchEvent(event);
    }

    //TODO
    private void handleMoveView(float x, float y)
    {
/*        int mHalfCenterViewWidth = mCenterViewWidth >> 1;

        //Radius为中心图标移动的限定的圆范围区域半径
        int Radius = mCenterViewWidth + mHalfCenterViewWidth;

		/*//*若用户手指移动的点与中心点的距离长度大于Radius，则中心图标坐标位置限定在移动区域范围圆弧上。
		/*//* 一般是用户拖动中心图标，手指移动到限定圆范围区域外。
        if (Math.sqrt(dist2(x - screenHalfWidth, y - (mCenterView.getTop() + mCenterViewWidth / 2)
        )) > Radius)
        {
            //原理为x1 / x = r1 / r
            x = (float) ((Radius / (Math.sqrt(dist2(x - screenHalfWidth, y - (mCenterView.getTop() + mHalfCenterViewWidth)
            )))) * (x - screenHalfWidth) + screenHalfWidth);

            y = (float) ((Radius / (Math.sqrt(dist2(x - screenHalfWidth, y - (mCenterView.getTop() + mHalfCenterViewWidth)
            )))) * (y - (mCenterView.getTop() + mHalfCenterViewWidth)) + mCenterView.getTop() + mHalfCenterViewWidth);
        }*/

        m_x = x;
        m_y = y;

		//*图形的坐标是以左上角为基准的，
		//* 所以，为了使手指所在的坐标和图标的中心位置一致，
	    //* 中心坐标要减去宽度和高度的一半。
		//*//*
        m_centerView.setX((int)x - m_halfCenterViewWidth);
        m_centerView.setY((int)y - m_halfCenterViewHeight);
        ShowLightView(x, y);
        invalidate();
    }

    //TODO trigger function once finger move to that region
    private void doTriggerEvent(float x, float y)
    {
        if(x < m_quarterScreenWidth)
        {
            onAnimationEnd();
            setTargetViewInvisible(m_startAdView);
            //发送消息到MainActivity类中的mHandler出来 TODO
            //mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_SMS).sendToTarget();
        }
        else if(x > (m_screenWidth - m_quarterScreenWidth))
        {
            onAnimationEnd();
            setTargetViewInvisible(m_unlockView);
            //发送消息到MainActivity类中的mHandler出来 TODO
            //mainHandler.obtainMessage(MainActivity.MSG_LAUNCH_SMS).sendToTarget();
        }
    }

    //重置中心图标，回到原位置
    private void resetMoveView()
    {
        m_centerView.setX(m_screenWidth / 2 - m_halfCenterViewWidth);
        m_centerView.setY((m_centerView.getTop() + m_halfCenterViewHeight) - m_halfCenterViewHeight);
        onAnimationStart();
        invalidate();
    }

    private void setTargetViewVisible(float x, float y)
    {
/*        if(Math.sqrt(dist2(x - screenHalfWidth, y - (mCenterView.getTop() + mCenterViewWidth / 2)
        )) > m_alphaViewHeight / 4)
        {
            if (MusicInfo.isMusic())
        {
            setMusicButtonVisibility(false);
        }
        else
        {
            return;
        }
        }*/

        return;
    }

    private void ShowLightView(float x, float y)
    {
        if(x < m_quarterScreenWidth)
        {
            setLightVisible(m_startAdLightView);
        }
        else if(x > (m_screenWidth - m_quarterScreenWidth))
        {
            setLightVisible(m_unlockLightView);
        }
        else
        {
            setLightInvisible();
        }
    }

    //隐藏高亮图标
    private void setLightInvisible()
    {
        final View mActivatedViews[] = {m_startAdLightView, m_unlockLightView};
        for (View view : mActivatedViews)
        {
            view.setVisibility(View.INVISIBLE);
        }

        m_centerView.setVisibility(View.VISIBLE);
    }

    private void setLightVisible(ImageView view)
    {
        view.setVisibility(View.VISIBLE);
        //m_centerView.setVisibility(View.INVISIBLE);
    }

    public void onClick(View v) {
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

        if(changed)
        {
            m_screenWidth = r;
            m_screenHeight = b;
            m_quarterScreenWidth = m_screenWidth/4;

            getViewMeasure();
            m_centerViewTop = 4 * m_screenHeight / 7 - (m_centerViewDimension.height >> 1);
            m_centerViewBottom = 4 * m_screenHeight / 7 + (m_centerViewDimension.height >> 1);
            m_alphaViewTop = 4 * m_screenHeight / 7 - (m_centerViewDimension.height >> 1);
            m_alphaViewBottom = 4 * m_screenHeight / 7 + (m_centerViewDimension.height >> 1);

            setChildViewLayout();
            setActivatedViewLayout();
        }

        if(DBG) Log.d(TAG, "changed-->" + changed);
        if(DBG) Log.d(TAG, "l-->" + l);
        if(DBG) Log.d(TAG, "t-->" + t);
        if(DBG) Log.d(TAG, "r-->" + r);
        if(DBG) Log.d(TAG, "b-->" + b);
    }

    //设置各图标在FxLockView中的布局
    private void setChildViewLayout()
    {
        int screenHalfWidth = m_quarterScreenWidth*2;
        m_alphaView.layout(screenHalfWidth - m_alphaViewDimension.width >> 1, m_alphaViewTop,
                screenHalfWidth + m_alphaViewDimension.width >> 1, m_alphaViewBottom);

        m_centerView.layout(screenHalfWidth - m_centerViewDimension.width >> 1, m_centerViewTop,
                screenHalfWidth + m_centerViewDimension.width >> 1, m_centerViewBottom);

        m_startAdView.layout(0,
                (m_screenHeight - m_startAdViewDimension.height) >> 1 ,
                (m_centerViewDimension.width ),
                (m_screenHeight + m_startAdViewDimension.height) >> 1 );

        m_unlockView.layout(m_screenWidth - m_startAdViewDimension.width,
                (m_screenHeight - m_startAdViewDimension.height) >> 1,
                0,
                (m_screenHeight + m_startAdViewDimension.height) >> 1 );
    }

    //设置高亮图标的布局
    private void setActivatedViewLayout()
    {
        m_startAdLightView.layout(0,
                (m_screenHeight - m_startAdLightViewDimension.height) >> 1 ,
                m_startAdLightViewDimension.width,
                (m_screenHeight + m_startAdLightViewDimension.height) >> 1 );

        m_unlockLightView.layout(m_screenWidth - m_unlockLightViewDimension.width,
                (m_screenHeight - m_unlockLightViewDimension.height) >> 1,
                0,
                (m_screenHeight + m_unlockLightViewDimension.height) >> 1 );
    }

    //获取各个图标的宽、高
    private void getViewMeasure()
    {
        m_alphaView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        m_alphaViewDimension.width = m_alphaView.getMeasuredWidth();
        m_alphaViewDimension.height = m_alphaView.getMeasuredHeight();

        m_centerView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        m_centerViewDimension.width = m_centerView.getMeasuredWidth();
        m_centerViewDimension.height = m_centerView.getMeasuredHeight();

        m_startAdView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        m_startAdViewDimension.width = (m_startAdView.getMeasuredWidth());// >> 1; ?? why devided by 2
        m_startAdViewDimension.height = (m_startAdView.getMeasuredHeight());

        m_unlockView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        m_unlockViewDimension.width = (m_unlockView.getMeasuredWidth());
        m_unlockViewDimension.height = (m_unlockView.getMeasuredHeight());

        m_startAdLightView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        m_startAdLightViewDimension.width = (m_startAdLightView.getMeasuredWidth());
        m_startAdLightViewDimension.height = (m_startAdLightView.getMeasuredHeight());

        m_unlockLightView.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        m_unlockLightViewDimension.width = (m_unlockLightView.getMeasuredWidth());
        m_unlockLightViewDimension.height = (m_unlockLightView.getMeasuredHeight());
    }

    //获取图标，将获取的图标添加入FxLockView，设置图标的可见性
    private void initViews(Context context) {
        m_alphaView = new ImageView(context);
        m_alphaView.setImageResource(R.drawable.centure2);
        setViewsLayout(m_alphaView);
        m_alphaView.setVisibility(View.INVISIBLE);
        if(DBG) Log.d(TAG, "m_alphaView");
        m_centerView = new ImageView(context);
        m_centerView.setImageResource(R.drawable.centure1);
        setViewsLayout(m_centerView);
        m_centerView.setVisibility(View.VISIBLE);
        if(DBG) Log.d(TAG, "m_centerView");
        m_unlockView = new ImageView(context);
        m_unlockView.setImageResource(R.drawable.unlock);
        setViewsLayout(m_unlockView);
        m_unlockView.setVisibility(View.VISIBLE);
        if(DBG) Log.d(TAG, "m_unlockView");
        m_startAdView = new ImageView(context);
        m_startAdView.setImageResource(R.drawable.dial);
        setViewsLayout(m_startAdView);
        m_startAdView.setVisibility(View.VISIBLE);
        if(DBG) Log.d(TAG, "m_startAdView");
        m_startAdLightView= new ImageView(context);
        setLightDrawable(m_startAdLightView);
        setViewsLayout(m_startAdLightView);
        m_startAdLightView.setVisibility(INVISIBLE);
        if(DBG) Log.d(TAG, "m_startAdLightView");
        m_startAdLightView = new ImageView(context);
        setLightDrawable(m_startAdLightView);
        setViewsLayout(m_startAdLightView);
        m_startAdLightView.setVisibility(INVISIBLE);

        m_unlockLightView = new ImageView(context);
        setLightDrawable(m_unlockLightView);
        setViewsLayout(m_unlockLightView);
        m_unlockLightView.setVisibility(INVISIBLE);
    }

    private void setViewsLayout(ImageView currentImageView) {
        currentImageView.setScaleType(ImageView.ScaleType.CENTER);
        currentImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        addView(currentImageView);
    }

    private void setLightDrawable(ImageView view)
    {
        view.setImageResource(R.drawable.light);
    }

    private void setTargetViewInvisible(ImageView view)
    {
        view.setVisibility(View.INVISIBLE);
    }

    /*
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // TODO: consider storing these as member variables to reduce
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
            }
        }
    */
    //停止中心图标动画
    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        if (alpha != null)
        {
            alpha = null;
        }
        m_alphaView.setAnimation(null);
    }

    //显示中心图标动画
    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        m_alphaView.setVisibility(View.VISIBLE);

        if (alpha == null) {
            alpha = new AlphaAnimation(0.0f, 1.0f);
            alpha.setDuration(1000);
        }
        alpha.setRepeatCount(Animation.INFINITE);
        m_alphaView.startAnimation(alpha);
    }

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
