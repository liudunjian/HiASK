package com.hisense.hiask.robot.multi;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.robot.RobotAudioMsgBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.DateUtils;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liudunjian on 2018/5/15.
 */

public class RobotMsgAudioViewProvider extends BaseViewProvider<RobotAudioMsgBean, RobotMsgAudioViewProvider.ViewHolder> {

    private IRobotMsgAudioItemListener audioItemListener;

    public RobotMsgAudioViewProvider(IRobotMsgAudioItemListener audioItemListener) {
        super();
        this.audioItemListener = audioItemListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_robot_message_audio_item_layout, parent, false), audioItemListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RobotAudioMsgBean item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.robot_message_audio_time)
        TextView robotMessageAudioTime;
        @BindView(R.id.robot_message_audio_header_l)
        ImageView robotMessageAudioHeaderL;
        @BindView(R.id.robot_message_audio_content)
        ImageView robotMessageAudioContent;
        @BindView(R.id.robot_message_audio_header_r)
        ImageView robotMessageAudioHeaderR;

        private IRobotMsgAudioItemListener listener;
        private RobotAudioMsgBean item;

        public ViewHolder(View itemView, IRobotMsgAudioItemListener listener) {
            super(itemView);
            this.listener = listener;
        }

        public void bindData(RobotAudioMsgBean item) {
            if (EmptyUtils.isEmpty(item))
                return;
            this.item = item;
            if (getAdapterPosition() == 0) { //|| message.getSendTime().getTime() - ((BotMessage)this.messageData.get(position - 1)).getSendTime().getTime() > 300000L
                robotMessageAudioTime.setVisibility(View.VISIBLE);
                robotMessageAudioTime.setText(DateUtils.format(item.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
            } else {
                robotMessageAudioTime.setVisibility(View.GONE);
            }
            robotMessageAudioHeaderL.setVisibility(item.getDirection() == 1 ? View.VISIBLE : View.INVISIBLE);
            robotMessageAudioHeaderR.setVisibility(item.getDirection() == 0 ? View.VISIBLE : View.INVISIBLE);
            robotMessageAudioContent.setBackgroundResource(item.getDirection() == 1 ? R.drawable.robot_message_bubble_left : R.drawable.robot_message_bubble_right);
            robotMessageAudioContent.setImageResource(item.getDirection() == 1 ? R.drawable.robot_message_audio_left : R.drawable.robot_message_audio_right);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) robotMessageAudioContent.getLayoutParams();
            layoutParams.gravity = item.getDirection() == 0 ? Gravity.RIGHT : Gravity.LEFT;
            robotMessageAudioContent.setLayoutParams(layoutParams);
        }

        @OnClick(R.id.robot_message_audio_content_layout)
        public void onViewClicked() {
            if (EmptyUtils.isNotEmpty(listener))
                listener.onRobotMsgAudioItemClicked(item);
        }
    }

    public interface IRobotMsgAudioItemListener {
        void onRobotMsgAudioItemClicked(RobotAudioMsgBean item);
    }
}
