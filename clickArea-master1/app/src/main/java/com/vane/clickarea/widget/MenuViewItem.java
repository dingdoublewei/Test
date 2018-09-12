package com.vane.clickarea.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class MenuViewItem extends FrameLayout {

    private int width = -1;
    private int height = -1;
    private Bitmap bitmap;
    private Bitmap newBitmap;
    private static final String TAG = "MenuViewItem";
    private int count = 0;

    public MenuViewItem(Context context) {
        super(context);
    }

    public MenuViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MenuViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            System.out
                    .println("MenuViewItem  -> onDraw() Canvas: trying to use a recycled bitmap");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "count" + count++);
        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (width == -1 || height == -1) {
            width = getWidth();
            height = getHeight();

            Drawable drawable = ((StateListDrawable) getBackground()).getCurrent();

            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?
                    Bitmap.Config.ARGB_4444 : Bitmap.Config.RGB_565;
            // 建立对应 bitmap
            bitmap = Bitmap.createBitmap(width, height, config);
            // 建立对应 bitmap 的画布
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            // 把 drawable 内容画到画布中
            drawable.draw(canvas);

        }

        if (null == bitmap || x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }

        int pixel = bitmap.getPixel(x, y);

        if (bitmap != null && !bitmap.isRecycled()) {
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
            width = -1;
            height = -1;
        }

        if (Color.TRANSPARENT == pixel) {
            return false;
        }
        return super.onTouchEvent(event);
    }


}
