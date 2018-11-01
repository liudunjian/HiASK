package com.hisense.hiask.robot.multi;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.robot.RobotMenuRecBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/4/28.
 */

public class RobotMenuRecChildViewProvider extends BaseViewProvider<RobotMenuRecBean, RobotMenuRecChildViewProvider.ViewHolder> {

    private IRobotMenuRecItemListener listener;

    public RobotMenuRecChildViewProvider(IRobotMenuRecItemListener listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.list_robot_msg_menu_item_layout, parent, false), this.listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RobotMenuRecBean item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.robot_msg_menu_item)
        TextView robotMsgMenuRecItem;

        private IRobotMenuRecItemListener listener;
        private RobotMenuRecBean item;

        public ViewHolder(View itemView, IRobotMenuRecItemListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(RobotMenuRecBean item) {
            this.item = item;
            robotMsgMenuRecItem.setText(item.getContent());
        }

        @OnClick(R.id.robot_msg_menu_item)
        public void onViewClicked() {
            if (EmptyUtils.isNotEmpty(this.listener)) {
                listener.onRobotMenuRecItemClicked(item);
            }
        }
    }

    public interface IRobotMenuRecItemListener {
        void onRobotMenuRecItemClicked(RobotMenuRecBean item);
    }
}
