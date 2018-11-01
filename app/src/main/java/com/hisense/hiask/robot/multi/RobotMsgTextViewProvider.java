package com.hisense.hiask.robot.multi;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hiask.robot.adapter.RobotRichMsgHolder;
import com.hisense.hibeans.robot.IRobotBeanDateTime;
import com.hisense.hibeans.robot.RobotTextMsgBean;
import com.hisense.hibot.bot.client.BotClient;
import com.hisense.hibot.bot.enumdefine.EBotConnectState;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.DateUtils;
import com.hisense.hitools.utils.EmptyUtils;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.MultiTypeAdapter;

import static android.view.View.TEXT_ALIGNMENT_VIEW_END;
import static android.view.View.TEXT_ALIGNMENT_VIEW_START;

/**
 * Created by liudunjian on 2018/4/27.
 */

public class RobotMsgTextViewProvider extends BaseViewProvider<RobotTextMsgBean, RobotMsgTextViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_robot_message_text_item_layout, parent, false), getAdapter());
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RobotTextMsgBean item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.robot_message_text_time)
        TextView robotMessageTextTime;
        @BindView(R.id.robot_message_text_header_l)
        ImageView robotMessageTextHeaderL;
        @BindView(R.id.robot_message_text_content)
        TextView robotMessageTextContent;
        @BindView(R.id.robot_message_text_content_layout)
        LinearLayout robotMessageTextContentLayout;
        @BindView(R.id.robot_message_text_header_r)
        ImageView robotMessageTextHeaderR;
        @BindView(R.id.robot_message_more_action)
        TextView robotMessageMoreAction;
        @BindView(R.id.robot_message_text_content_item_layout)
        LinearLayout robotMessageTextContentItemLayout;
        @BindView(R.id.robot_message_text_send_error)
        ImageView robotMessageTextSendError;
        @BindView(R.id.robot_message_text_send_loading)
        ProgressBar robotMessageTextSendLoading;

        private RobotRichMsgHolder holder;
        private MultiTypeAdapter adapter;

        public ViewHolder(View itemView, MultiTypeAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }

        public void bindData(RobotTextMsgBean item) {
            if (EmptyUtils.isEmpty(item))
                return;

            if (getAdapterPosition() == 0 || item.getTime() -
                    ((IRobotBeanDateTime) adapter.getItems().get(getAdapterPosition())).getTime()
                    > 30000L) {
                robotMessageTextTime.setVisibility(View.VISIBLE);
                robotMessageTextTime.setText(DateUtils.format(item.getTime(), "HH:mm:ss"));
            } else {
                robotMessageTextTime.setVisibility(View.GONE);
            }

            robotMessageTextSendLoading.setVisibility(View.GONE);

            if (item.getDirection() == 1) {
                robotMessageTextHeaderL.setVisibility(View.VISIBLE);
                robotMessageTextHeaderR.setVisibility(View.INVISIBLE);
                robotMessageTextSendError.setVisibility(View.GONE);
                robotMessageTextSendLoading.setVisibility(View.GONE);
                robotMessageTextContent.setBackgroundResource(R.drawable.robot_message_bubble_left);
                //robotMessageTextContent.setTextAlignment(TEXT_ALIGNMENT_VIEW_START);


            } else if (item.getDirection() == 0) {
                robotMessageTextHeaderL.setVisibility(View.INVISIBLE);
                robotMessageTextHeaderR.setVisibility(View.VISIBLE);
                robotMessageTextContent.setBackgroundResource(R.drawable.robot_message_bubble_right);
                //robotMessageTextContent.setTextAlignment(TEXT_ALIGNMENT_VIEW_END);

                if (BotClient.getInstance(itemView.getContext()).getConnectState() != EBotConnectState.BOT_CONNECTED_STATE) {
                    robotMessageTextSendError.setVisibility(View.VISIBLE);
                } else {
                    robotMessageTextSendError.setVisibility(View.GONE);
                }
            }

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) robotMessageTextContentItemLayout.getLayoutParams();
            layoutParams.gravity = item.getDirection() == 0 ? Gravity.RIGHT : Gravity.LEFT;
            robotMessageTextContentItemLayout.setLayoutParams(layoutParams);

            holder = new RobotRichMsgHolder(item.getContent());
            if (holder.isNeededSplit() && item.getDirection() == 1) {
                holder.splitContentToParts();
                robotMessageMoreAction.setVisibility(View.VISIBLE);
                robotMessageTextContent.setText(holder.nextContent());
            } else {
                robotMessageMoreAction.setVisibility(View.GONE);
                robotMessageTextContent.setText(item.getContent());
            }
        }

        @OnClick(R.id.robot_message_more_action)
        public void onViewClicked() {
            String appendContent = holder.nextContent();
            if (EmptyUtils.isEmpty(appendContent)) {
                robotMessageMoreAction.setVisibility(View.GONE);
                return;
            }
            robotMessageTextContent.append(appendContent);
        }
    }
}
