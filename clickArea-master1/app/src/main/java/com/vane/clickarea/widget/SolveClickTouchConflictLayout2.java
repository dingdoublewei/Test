package com.vane.clickarea.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.vane.demo.R;

/**
 * Created by Yuchao on 2018/8/30.
 */

public class SolveClickTouchConflictLayout2 extends RelativeLayout implements View.OnClickListener {
    public final static int idStart = 100;
    public Context mContext;
    public int itemCount = -1;
    public int mWidth = 1000;
    public int mHeight = 500;
    public int direction = 1;//设置当前布局的方向 1,2,3,4 分别对应不同的res数组
    private static final String TAG = "SolveLayout2";
    //23
    public int resArray1[] = {R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5,
            R.drawable.car6, R.drawable.car7, R.drawable.car8, R.drawable.car9, R.drawable.car10, R.drawable.car11, R.drawable.car12,
            R.drawable.car13, R.drawable.car14, R.drawable.car15, R.drawable.car16, R.drawable.car17, R.drawable.car18,
            R.drawable.car19, R.drawable.car20, R.drawable.car21, R.drawable.car22, R.drawable.car23};
    //19
    public int resArray2[] =
            {R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5,
                    R.drawable.car6, R.drawable.car8, R.drawable.car9, R.drawable.car10, R.drawable.car11,
                    R.drawable.car12, R.drawable.car13, R.drawable.car17, R.drawable.car18,
                    R.drawable.car19, R.drawable.car20, R.drawable.car21, R.drawable.car22, R.drawable.car23};
    //20
    public int resArray3[] = {R.drawable.car4, R.drawable.car5,
            R.drawable.car6, R.drawable.car7, R.drawable.car8, R.drawable.car9, R.drawable.car10, R.drawable.car11, R.drawable.car12, R.drawable.car13
            , R.drawable.car14, R.drawable.car15, R.drawable.car16, R.drawable.car17, R.drawable.car18, R.drawable.car19, R.drawable.car20
            , R.drawable.car21, R.drawable.car22, R.drawable.car23};
    //19
    public int resArray4[] = {R.drawable.car1, R.drawable.car2, R.drawable.car3, R.drawable.car4, R.drawable.car5,
            R.drawable.car6, R.drawable.car7, R.drawable.car8, R.drawable.car9, R.drawable.car10, R.drawable.car11,
            R.drawable.car12, R.drawable.car13, R.drawable.car14, R.drawable.car15, R.drawable.car16, R.drawable.car17,
            R.drawable.car18, R.drawable.car19,};

    public int idArray1[] = {idStart + 1, idStart + 2, idStart + 3, idStart + 4, idStart + 5,
            idStart + 6, idStart + 7, idStart + 8, idStart + 9, idStart + 10, idStart + 11,
            idStart + 12, idStart + 13, idStart + 14, idStart + 15, idStart + 16, idStart + 17, idStart + 18,
            idStart + 19, idStart + 20, idStart + 21, idStart + 22, idStart + 23};

    public int idArray2[] = {idStart + 1, idStart + 2, idStart + 3, idStart + 4, idStart + 5,
            idStart + 6, idStart + 8, idStart + 9, idStart + 10, idStart + 11,
            idStart + 12, idStart + 13, idStart + 17, idStart + 18,
            idStart + 19, idStart + 20, idStart + 21, idStart + 22, idStart + 23};

    public int idArray3[] = {idStart + 4, idStart + 5,
            idStart + 6, idStart + 7, idStart + 8, idStart + 9, idStart + 10, idStart + 11,
            idStart + 12, idStart + 13, idStart + 14, idStart + 15, idStart + 16, idStart + 17, idStart + 18,
            idStart + 19, idStart + 20, idStart + 21, idStart + 22, idStart + 23};

    public int idArray4[] = {idStart + 1, idStart + 2, idStart + 3, idStart + 4, idStart + 5,
            idStart + 6, idStart + 7, idStart + 8, idStart + 9, idStart + 10, idStart + 11,
            idStart + 12, idStart + 13, idStart + 14, idStart + 15, idStart + 16, idStart + 17, idStart + 18,
            idStart + 19,};

    public SolveClickTouchConflictLayout2(Context context) {
        this(context, null);
        mContext = context;
    }

    public SolveClickTouchConflictLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.SolveClickTouchConflictLayout2);
        itemCount = ta.getInteger(R.styleable.SolveClickTouchConflictLayout2_itemCount, -1);
        direction = ta.getInteger(R.styleable.SolveClickTouchConflictLayout2_carDirection, -1);
        initView();
        ta.recycle();
    }

    public SolveClickTouchConflictLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 初始化view
     */
    private void initView() {
        int[] resTemp = null;
        int[] idTemp = null;
        switch (direction) {
            case 1:
                resTemp = resArray1;
                idTemp = idArray1;
                break;
            case 2:
                resTemp = resArray2;
                idTemp = idArray2;
                break;
            case 3:
                resTemp = resArray3;
                idTemp = idArray3;
                break;
            case 4:
                resTemp = resArray4;
                idTemp = idArray4;
                break;
        }

        if (itemCount < 0) {
            throw new IllegalArgumentException("itemCount can't < 0 ");
        }

        if (resTemp.length != itemCount) {
            throw new IndexOutOfBoundsException("resTemp.length must be itemCount---" + resTemp.length + "," + itemCount);
        }
        if (idTemp.length != itemCount) {
            throw new IndexOutOfBoundsException("idTemp.length must be itemCount---" + idTemp.length + "," + itemCount);
        }

        LayoutParams params = new LayoutParams(mWidth, mHeight);
        params.addRule(CENTER_IN_PARENT);
        for (int i = 0; i < itemCount; i++) {
            MenuViewItem item = getViewById(this, idTemp[i]);
            Log.d(TAG, "direction= " + direction);
            if (item == null) {
                item = new MenuViewItem(getContext());
                item.setId(idTemp[i]);
                addView(item, params);
                Log.d(TAG, "item == null");
            } else {
                Bitmap tag = (Bitmap) item.getTag();
                Log.d(TAG, item.getId() + "item != null");
                if (null != tag) {
                    Log.d(TAG, item.getId() + "null != tag");
                    tag.recycle();
                    tag = null;
                }
            }
            item.setBackgroundResource(resTemp[i]);
            item.setOnClickListener(this);
        }
    }


    private boolean mScrolling;
    private float touchDownX;
    private float touchDownY;

    //拦截触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getX();
                touchDownY = event.getY();
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(touchDownX - event.getX()) >= ViewConfiguration.get(getContext()).getScaledTouchSlop() || Math.abs(touchDownY - event.getY()) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    mScrolling = true;
                } else {
                    mScrolling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mScrolling = false;
                break;
        }
        return mScrolling;
    }

    float x2 = 0;
    float y2 = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;//消费触摸事件
            case MotionEvent.ACTION_MOVE:
                //如果注释掉 当前的 就会在滑动的时候就会有对应的响应
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                //左滑
                if (touchDownX - x2 > 50) {
                    if (mOnSlideListener != null) {
                        mOnSlideListener.onRightToLeftSlide();
                    }
                }
                //右滑
                if (touchDownX - x2 < -50) {
                    if (mOnSlideListener != null) {
                        mOnSlideListener.onLeftToRightSlide();
                    }
                }
                //上滑
//                if (touchDownY - y2 > DisPlayUtils.dip2px(getContext(), 40)) {
//                    if (mOnSlideListener != null) {
//                        mOnSlideListener.onDownToUpSlide();
//                    }
//                }
//                //下滑
//                if (touchDownY - y2 < -DisPlayUtils.dip2px(getContext(), 40)) {
//                    if (mOnSlideListener != null) {
//                        mOnSlideListener.onUpToDownSlide();
//                    }
//                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private OnSlideListener mOnSlideListener;

    public OnSlideListener getOnSlideListener() {
        return mOnSlideListener;
    }

    public void setmSetOnSlideListener(OnSlideListener mOnSlideListener) {
        this.mOnSlideListener = mOnSlideListener;
    }

    public interface OnSlideListener {

        void onRightToLeftSlide();

        void onLeftToRightSlide();

        void onUpToDownSlide();

        void onDownToUpSlide();
    }

    public ItemClickListener mItemClickListener;//item点击监听

    public interface ItemClickListener {
        void onMyClick(View v);
    }

    /**
     * 设置item点击回调
     *
     * @param listener 回调
     */
    public void setOnItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        v.setSelected(!v.isSelected());
        if (mItemClickListener != null) {
            mItemClickListener.onMyClick(v);
        }
    }

    public static <T extends View> T getViewById(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (null == viewHolder) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (null == childView) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}
