package com.hisense.hiask.robot.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisense.hiask.hiask.R;

/**
 * Created by liudunjian on 2018/5/10.
 */

public class RecordViewHolder {

    private Context context;
    private ImageView recordVolume;
    private TextView recordState;
    private boolean cancel;
    private float volume;
    private static final int[] VolumeDrawable;

    public RecordViewHolder(View rootView) {
        this.context = rootView.getContext();
        this.recordVolume = (ImageView) rootView.findViewById(R.id.robot_record_volume);
        this.recordState = (TextView) rootView.findViewById(R.id.robot_record_state);
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
        if (cancel) {
            this.recordState.setText(R.string.robot_record_release_and_cancel);
            this.recordState.setBackgroundResource(R.color.color_robot_record_cancel);
            this.recordVolume.setImageDrawable(this.context.getResources().getDrawable(R.drawable.robot_record_cancel));
        } else {
            this.recordState.setText(R.string.robot_record_slide_up_and_cancel);
            this.recordState.setBackgroundResource(R.color.color_robot_record_finish);
            this.setVolumeImage();
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (!this.cancel) {
            this.setVolumeImage();
        }
    }

    public boolean isCancel() {
        return this.cancel;
    }

    public float getVolume() {
        return this.volume;
    }

    private void setVolumeImage() {
        int level = (int) Math.floor((double) (this.volume * 0.01 * (float) VolumeDrawable.length));
        if (level >= VolumeDrawable.length) {
            level = VolumeDrawable.length - 1;
        }

        int drawable = VolumeDrawable[level];
        this.recordVolume.setImageDrawable(this.context.getResources().getDrawable(drawable));
    }

    static {
        VolumeDrawable = new int[]{R.drawable.robot_record_volume_1, R.drawable.robot_record_volume_2, R.drawable.robot_record_volume_3, R.drawable.robot_record_volume_4, R.drawable.robot_record_volume_5, R.drawable.robot_record_volume_6, R.drawable.robot_record_volume_7, R.drawable.robot_record_volume_8};
    }
}
