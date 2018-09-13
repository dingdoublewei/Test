package com.vane.clickarea;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.vane.clickarea.widget.MenuViewItem;
import com.vane.clickarea.widget.MyViewPager;
import com.vane.clickarea.widget.SolveClickTouchConflictLayout2;
import com.vane.demo.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements MyViewPager.OnTouchEventListener, SolveClickTouchConflictLayout2.ItemClickListener {

    private MyViewPager vp;
    private List<View> data = new ArrayList<View>();
    private MyViewPageAdapter pageAdapter;
    private float x;
    private static final String TAG = "MainActivity";
    private Toast mToast;
    private int currentPos;
    boolean selectedStatus[];
    private float scrollDelta = 50;//ViewPager切换条目的生效距离
    public Map<Integer, Boolean> statusContainers = new LinkedHashMap<Integer, Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView() {
        vp = (MyViewPager) findViewById(R.id.vp);
        vp.setOffscreenPageLimit(4);
        vp.setSlide(false);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected position= " + position);
                currentPos = position;
                setCurrentItemStatus();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged state= " + state);
            }
        });

    }

    /**
     * 设置当前条目的选中状态
     */
    private void setCurrentItemStatus() {
        View view = data.get(currentPos);
        SolveClickTouchConflictLayout2 rl_root_item = (SolveClickTouchConflictLayout2) view.findViewById(R.id.rl_root_item);
        if (null != rl_root_item) {
            for (int i = 0; i < rl_root_item.getChildCount(); i++) {
                MenuViewItem item = (MenuViewItem) rl_root_item.getChildAt(i);
                Boolean status = statusContainers.get(item.getId());
                Log.d(TAG, "setCurrentItemStatus id= " + item.getId() + "--" + item.isSelected());
                if (status == null) {
                    status = false;
                }
                item.setSelected(status);
            }
        }
    }

    private void initData() {
        data.add(initItem("001", R.layout.direction1));
        data.add(initItem("002", R.layout.direction2));
        data.add(initItem("003", R.layout.direction3));
        data.add(initItem("004", R.layout.direction4));
        pageAdapter = new MyViewPageAdapter(data, this);
        vp.setAdapter(pageAdapter);
        startAnimByXml();
        selectedStatus = new boolean[data.size()];
    }

    private View initItem(String text, int res) {
        View view = LayoutInflater.from(this).inflate(res, null);
        SolveClickTouchConflictLayout2 rl_root_item = (SolveClickTouchConflictLayout2) view.findViewById(R.id.rl_root_item);
        rl_root_item.setOnItemClickListener(this);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    /**
     * 切换到下一页
     */
    private void nextPage() {
        int index = (vp.getCurrentItem() + 1) % data.size();
        vp.setCurrentItem(index, false);
        Log.d("nextPage", "index= " + index);
    }

    /**
     * 切换到前一页
     */
    private void prePage() {
        int index = (vp.getCurrentItem() - 1) % data.size();
        if (index < 0) {
            index = data.size();
        }
        Log.d("prePage", "index= " + index);
        vp.setCurrentItem(index, false);
    }

    @Override
    public void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                Log.d("onTouch ACTION_DOWN", "x= " + x);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("onTouch ACTION_MOVE", "x= " + event.getX());
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float delta = event.getX() - x;
                x = event.getX();
                //右滑
                if (delta > 0 && delta > scrollDelta) {
                    nextPage();
                }
                //左滑
                if (delta < 0 && (-delta) > scrollDelta) {
                    prePage();
                }
                Log.d("onTouch ACTION_UP", "x= " + x);
                break;

        }
    }

    @Override
    public void onMyClick(View v) {
        mToast = Toast.makeText(this, "onMyClick", Toast.LENGTH_SHORT);
        mToast.show();
        statusContainers.put(v.getId(), v.isSelected());
        Log.d(TAG, "onMyClick id= " + v.getId() + "--" + v.isSelected());
    }

    private void startAnimByXml() {
        Animation anim_scale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        vp.startAnimation(anim_scale);
        anim_scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                vp.setOnTouchEventListener(MainActivity.this);//在动画结束后注册触摸事件
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
