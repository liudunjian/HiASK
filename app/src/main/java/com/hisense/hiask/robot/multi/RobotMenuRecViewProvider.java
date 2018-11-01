package com.hisense.hiask.robot.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.robot.RobotMenuRecBean;
import com.hisense.hibeans.robot.RobotMenuRecList;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.himultitype.view.LinearMultiView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liudunjian on 2018/4/28.
 */

public class RobotMenuRecViewProvider extends BaseViewProvider<RobotMenuRecList, RobotMenuRecViewProvider.ViewHolder> {

    private RobotMenuRecChildViewProvider.IRobotMenuRecItemListener menuRecChildListener;

    public RobotMenuRecViewProvider(RobotMenuRecChildViewProvider.IRobotMenuRecItemListener menuRecChildListener) {
        super();
        this.menuRecChildListener = menuRecChildListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_robot_menu_rec_list_layout, parent, false), menuRecChildListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RobotMenuRecList item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.robot_msg_menu_rec_multiview)
        LinearMultiView robotMsgMenuRecMultiview;

        private RobotMenuRecChildViewProvider.IRobotMenuRecItemListener childItemListener;

        public ViewHolder(View itemView, RobotMenuRecChildViewProvider.IRobotMenuRecItemListener listener) {
            super(itemView);
            this.childItemListener = listener;
            initMultiView();
        }

        public void bindData(RobotMenuRecList item) {
            List<Object> data = new ArrayList<>();
            data.addAll(item.getRecommends());
            robotMsgMenuRecMultiview.resetItems(data);
        }

        private void initMultiView() {
            robotMsgMenuRecMultiview.getAdapter().register(RobotMenuRecBean.class, new RobotMenuRecChildViewProvider(childItemListener));
        }

    }

    static class RobotMsgMenuRecAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<RobotMenuRecBean> menuRecList;

        public RobotMsgMenuRecAdapter(Context context) {
            this.context = context;
        }

        public int getCount() {
            return this.menuRecList == null ? 0 : this.menuRecList.size();
        }

        public Object getItem(int position) {
            return this.menuRecList.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            RobotMenuRecBean item = null;
            TextView view = (TextView) convertView;
            if (view == null) {
                view = (TextView) LayoutInflater.from(this.context).inflate(R.layout.list_robot_msg_menu_item_layout, parent, false);
                item = this.menuRecList.get(position);
                view.setTag(item);
            } else {
                item = (RobotMenuRecBean) view.getTag();
            }
            view.setText(item.getContent());
            return view;
        }

        public void setMenuRecList(ArrayList<RobotMenuRecBean> menuRecList) {
            this.menuRecList = menuRecList;
            this.notifyDataSetChanged();
        }
    }
}
