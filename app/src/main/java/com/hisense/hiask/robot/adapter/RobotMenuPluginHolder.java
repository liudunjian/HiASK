package com.hisense.hiask.robot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.robot.RobotPluginItemBean;
import com.hisense.hitools.utils.EmptyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudunjian on 2018/5/5.
 */

public class RobotMenuPluginHolder implements View.OnClickListener {

    private Context context;
    private List<View> viewList = new ArrayList<>();
    private List<RobotPluginItemBean> pluginItems;

    private IRobotPluginClickListener robotPluginClickListener ;


    public RobotMenuPluginHolder(Context context) {
        this.context = context.getApplicationContext();
    }

    public IRobotPluginClickListener getRobotPluginClickListener() {
        return robotPluginClickListener;
    }

    public void setRobotPluginClickListener(IRobotPluginClickListener robotPluginClickListener) {
        this.robotPluginClickListener = robotPluginClickListener;
    }

    public void initializePluginItem(List<RobotPluginItemBean> pluginItems) {

        if (EmptyUtils.isEmpty(pluginItems))
            return;
        this.pluginItems = pluginItems;

        int index = 0;
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setColumnCount(4);
        gridLayout.setUseDefaultMargins(true);
        viewList.add(gridLayout);
        for (RobotPluginItemBean item : pluginItems) {

            if (index >= 8) {
                gridLayout = new GridLayout(context);
                gridLayout.setColumnCount(4);
                gridLayout.setUseDefaultMargins(true);
                viewList.add(gridLayout);
                index = 0;
            }

            View view = LayoutInflater.from(context).inflate(R.layout.view_robot_plugin_item_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.robot_plugin_item_title);
            ImageView imageView = (ImageView) view.findViewById(R.id.robot_plugin_item_image);
            textView.setText(item.getTitle());
            imageView.setImageResource(item.getImgRes());
            imageView.setTag(item.getTag());
            imageView.setOnClickListener(this);
            gridLayout.addView(view);

            ++index;
        }
    }

    public List<View> getViewList() {
        return viewList;
    }

    private void setViewList(List<View> viewList) {
        this.viewList = viewList;
    }

    @Override
    public void onClick(View v) {
        if(robotPluginClickListener!=null)
            robotPluginClickListener.onRobotPluginItemClicked(v);
    }

    public interface IRobotPluginClickListener {
        void onRobotPluginItemClicked(View view);
    }
}
