package com.hisense.hiask.guide.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liudunjian on 2018/6/13.
 */

public class GuideViewPageAdapter extends PagerAdapter {

    private List<View> pages;

    public GuideViewPageAdapter(List<View> pages) {
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return pages == null ? 0 : pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = pages.get(position);
        container.addView(view);
        return view;
    }
}
