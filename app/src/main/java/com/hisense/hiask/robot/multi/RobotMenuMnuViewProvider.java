package com.hisense.hiask.robot.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.robot.RobotMenuMnuList;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.himultitype.view.LinearMultiView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liudunjian on 2018/4/28.
 */

public class RobotMenuMnuViewProvider extends BaseViewProvider<RobotMenuMnuList, RobotMenuMnuViewProvider.ViewHolder> {

    RobotMenuMnuChildViewProvider.IRobotMenuMnuItemListener listener;

    public RobotMenuMnuViewProvider(RobotMenuMnuChildViewProvider.IRobotMenuMnuItemListener listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_robot_enu_mnu_list_layout, parent, false), this.listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RobotMenuMnuList item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.robot_msg_menu_header_l)
        ImageView robotMsgMenuHeaderL;
        @BindView(R.id.robot_msg_menu_mnu_multiview)
        LinearMultiView robotMsgMenuMnuMultiview;
        @BindView(R.id.robot_message_menu_header_r)
        ImageView robotMessageMenuHeaderR;

        RobotMenuMnuChildViewProvider.IRobotMenuMnuItemListener listener;

        public ViewHolder(View itemView, RobotMenuMnuChildViewProvider.IRobotMenuMnuItemListener listener) {
            super(itemView);
            this.listener = listener;
            initRobotMenuMnuMultiView();
        }

        public void bindData(RobotMenuMnuList item) {
            List<Object> data = new ArrayList<>();
            data.addAll(item.getRobotMenus());
            robotMsgMenuMnuMultiview.resetItems(data);
        }

        private void initRobotMenuMnuMultiView() {
            robotMsgMenuMnuMultiview.getAdapter().register(RobotMenuMnuList.RobotMenuMnuBean.class, new RobotMenuMnuChildViewProvider(listener));
        }
    }
}
