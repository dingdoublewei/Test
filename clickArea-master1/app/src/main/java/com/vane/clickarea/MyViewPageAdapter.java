package com.vane.clickarea;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dww on 2018/8/30.
 */

public class MyViewPageAdapter extends PagerAdapter {
    private List<View> data;
    private Context mContext;
    private static final String TAG = "MyViewPageAdapter";

    public MyViewPageAdapter(List<View> list, Context context) {
        data = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount= " + data.size());
        return data == null ? 0 : data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem position= " + position);
        container.addView(data.get(position));
        return data.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d(TAG, "isViewFromObject");
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem");
        ViewGroup viewGroup = (ViewGroup) object;
        container.removeView(viewGroup);
    }

}
