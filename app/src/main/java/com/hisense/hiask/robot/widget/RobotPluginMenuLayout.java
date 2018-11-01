package com.hisense.hiask.robot.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hisense.hiask.hiask.R;
import com.hisense.hiask.widget.indicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liudunjian on 2018/5/3.
 */

public class RobotPluginMenuLayout extends FrameLayout {

    @BindView(R.id.robot_plugin_menu_view_pager)
    ViewPager robotPluginMenuViewPager;
    @BindView(R.id.robot_plugin_menu_line_indicator)
    LinePageIndicator robotPluginMenuLineIndicator;


    public RobotPluginMenuLayout(@NonNull Context context) {
        this(context, null);
    }

    public RobotPluginMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RobotPluginMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRobotPluginMenuLayout(context);
    }

    private void initRobotPluginMenuLayout(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_robot_plugin_menu_layout, this);
        ButterKnife.bind(rootView);
    }

    public void addViewToViewPager(List<View> views) {
        final RobotPluginPageAdapter adapter = new RobotPluginPageAdapter(views);
        robotPluginMenuViewPager.setAdapter(adapter);
        robotPluginMenuLineIndicator.setViewPager(this.robotPluginMenuViewPager);
    }

    public static class RobotPluginPageAdapter extends PagerAdapter {

        List<View> views = new ArrayList<>();

        public RobotPluginPageAdapter(List<View> views) {
            super();
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(this.views.get(position));
        }
    }
}
