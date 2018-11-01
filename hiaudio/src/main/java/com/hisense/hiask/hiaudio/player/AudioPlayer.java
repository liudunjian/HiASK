package com.hisense.hiask.hiaudio.player;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.hisense.hitools.utils.LogUtils;

import java.io.IOException;

/**
 * Created by liudunjian on 2018/5/16.
 */

public class AudioPlayer implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();

    public AudioPlayer() {
        initAudioPlayer();
    }

    public void startPlay(String file) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(file);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        mediaPlayer.stop();
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void initAudioPlayer() {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    /***********************media player state listeners****************/
    @Override
    public void onPrepared(MediaPlayer mp) {
        LogUtils.d("onPrepared");
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        LogUtils.d("onCompletion");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
