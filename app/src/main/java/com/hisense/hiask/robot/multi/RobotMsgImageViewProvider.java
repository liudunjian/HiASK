package com.hisense.hiask.robot.multi;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;
import com.hisense.hibeans.robot.RobotImageMsgBean;
import com.hisense.himultitype.base.BaseViewHolder;
import com.hisense.himultitype.base.BaseViewProvider;
import com.hisense.hitools.utils.DateUtils;
import com.hisense.hitools.utils.EmptyUtils;

import java.io.File;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by liudunjian on 2018/5/7.
 */

public class RobotMsgImageViewProvider extends BaseViewProvider<RobotImageMsgBean, RobotMsgImageViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.multi_robot_message_image_item_layout, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull RobotImageMsgBean item) {
        holder.bindData(item);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.robot_message_image_time)
        TextView robotMessageImageTime;
        @BindView(R.id.robot_message_image_header_l)
        ImageView robotMessageImageHeaderL;
        @BindView(R.id.robot_message_image_content)
        GifImageView robotMessageImageContent;
        @BindView(R.id.robot_message_image_content_layout)
        FrameLayout robotMessageImageContentLayout;
        @BindView(R.id.robot_message_image_header_r)
        ImageView robotMessageImageHeaderR;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindData(RobotImageMsgBean item) {
            if (EmptyUtils.isEmpty(item))
                return;
            if (getAdapterPosition() == 0) { //|| message.getSendTime().getTime() - ((BotMessage)this.messageData.get(position - 1)).getSendTime().getTime() > 300000L
                robotMessageImageTime.setVisibility(View.VISIBLE);
                robotMessageImageTime.setText(DateUtils.format(item.getSendTime(), "yyyy-MM-dd HH:mm:ss"));
            } else {
                robotMessageImageTime.setVisibility(View.GONE);
            }
            robotMessageImageHeaderL.setVisibility(item.getDirection() == 1 ? View.VISIBLE : View.INVISIBLE);
            robotMessageImageHeaderR.setVisibility(item.getDirection() == 0 ? View.VISIBLE : View.INVISIBLE);
            robotMessageImageContent.setBackgroundResource(item.getDirection() == 1 ? R.drawable.robot_message_bubble_left : R.drawable.robot_message_bubble_right);

            File file = new File(robotMessageImageContent.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), item.getDataPath());
            robotMessageImageContent.setImageURI(Uri.fromFile(file));
            robotMessageImageContent.setTag(item);

            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) robotMessageImageContent.getLayoutParams();
            layoutParams.gravity = item.getDirection() == 0 ? Gravity.RIGHT : Gravity.LEFT;
            robotMessageImageContent.setLayoutParams(layoutParams);
        }
    }
}
