package com.hisense.hiask.guide;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hisense.hiask.guide.adapter.GuideViewPageAdapter;
import com.hisense.hiask.hiask.R;
import com.hisense.hiask.main.MainActivity;
import com.hisense.hiask.mvpbase.BaseActivity;
import com.hisense.hiask.widget.indicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/6/13.
 */

public class GuideActivity extends BaseActivity<IGuide, GuidePresenter> implements IGuide {

    @BindView(R.id.guide_view_pager)
    ViewPager guideViewPager;
    @BindView(R.id.guide_skip)
    TextView guideSkip;
    @BindView(R.id.guide_enter)
    TextView guideEnter;
    @BindView(R.id.guide_line_page_indicator)
    LinePageIndicator guideLinePageIndicator;

    /*****************base override methods*****************/
    @Override
    protected void initViewDisplay() {
        initViewPager();
    }

    @Override
    protected void initEventsAndListeners() {

    }

    @Override
    protected void initPresenter() {
        presenter = new GuidePresenter(this, this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_guide;
    }

    /******************mvp override methods********************/

    @Override
    public boolean isDataPrepared() {
        return false;
    }

    /*******************events and listeners******************/

    @OnClick({R.id.guide_skip, R.id.guide_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guide_skip:
            case R.id.guide_enter:
                this.finish();
                startActivitySafely(MainActivity.class);
                break;
        }
    }

    /*****************private methods************************/
    private void initViewPager() {

        guideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    guideSkip.setVisibility(View.VISIBLE);
                    guideEnter.setVisibility(View.GONE);
                } else if (position == 1) {
                    guideSkip.setVisibility(View.GONE);
                    guideEnter.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<View> pages = new ArrayList<>();

        View page1 = LayoutInflater.from(this).inflate(R.layout.view_guide_page1_layout, null);
        View page2 = LayoutInflater.from(this).inflate(R.layout.view_guide_page2_layout, null);
        pages.add(page1);
        pages.add(page2);

        GuideViewPageAdapter pageAdapter = new GuideViewPageAdapter(pages);
        guideViewPager.setAdapter(pageAdapter);
        guideLinePageIndicator.setViewPager(this.guideViewPager);

    }

}
