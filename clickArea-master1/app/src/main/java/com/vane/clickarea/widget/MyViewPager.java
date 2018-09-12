package com.vane.clickarea.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by dww on 2018/8/30.
 */

public class MyViewPager extends ViewPager {
    private boolean isSlide;

    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (null != mListener) {
            mListener.onTouch(ev);
        }
        return isSlide && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSlide && super.onTouchEvent(ev);

    }

    private OnTouchEventListener mListener;

    public void setOnTouchEventListener(OnTouchEventListener listener) {
        this.mListener = listener;
    }

    /**
     *
     */
    public void removeOnTouchEventListener() {
        if (mListener != null) {
            mListener = null;
        }
    }

    public interface OnTouchEventListener {
        void onTouch(MotionEvent ev);
    }
}
