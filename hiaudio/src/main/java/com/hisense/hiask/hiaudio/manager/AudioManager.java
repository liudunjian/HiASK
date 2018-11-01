package com.hisense.hiask.hiaudio.manager;

import android.content.Context;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;

import com.hisense.hiask.hiaudio.player.AudioPlayer;
import com.hisense.hiask.hiaudio.record.IRecordingRMSListener;
import com.hisense.hiask.hiaudio.record.IRecordingStateListener;
import com.hisense.hiask.hiaudio.record.PCMRecorder;
import com.hisense.hitools.utils.AppUtils;
import com.hisense.hitools.utils.FileUtils;
import com.hisense.hitools.utils.HiUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by liudunjian on 2018/5/11.
 */

public class AudioManager {

    private static volatile AudioManager audioManager;

    private Context context;

    private PCMRecorder pcmRecorder;
    private AudioPlayer audioPlayer;

    private AudioConfig audioConfig;
    private File recordFile;
    private IRecordingStateListener recordingStateListener;

    public static AudioManager getInstance(Context context) {
        if (audioManager == null) {
            synchronized (AudioManager.class) {
                if (audioManager == null)
                    audioManager = new AudioManager(context);
            }
        }
        return audioManager;
    }

    private AudioManager(Context context) {
        this.context = context.getApplicationContext();
        initAudioConfig();
        this.pcmRecorder = new PCMRecorder(this.audioConfig);
        this.audioPlayer = new AudioPlayer();
    }

    public void startRecord(IRecordingRMSListener rmsListener) throws Exception {
        generateRecordFile();
        pcmRecorder.startRecord(this.recordFile, rmsListener);
    }

    public File finishRecord() {
        pcmRecorder.finishRecord();
        byte[] header = this.generateWAVHeader(audioConfig, FileUtils.getFileSize(this.recordFile));
        FileUtils.insertFile(recordFile.getAbsolutePath(), header, 0, header.length);
        return recordFile;
    }

    public void cancelRecord() {
        pcmRecorder.finishRecord();
        recordFile.delete();
    }

    public void releaseRecord() {
        pcmRecorder.releaseRecord();
    }

    public void startPlay(String file) {
        audioPlayer.startPlay(file);
    }

    public void stopPlay() {
        audioPlayer.stopPlay();
    }

    public void releasePlayer() {
        audioPlayer.release();
    }

    public void releaseAllAudioRes() {
        releaseRecord();
        releasePlayer();
    }

    private void generateRecordFile() {
        String parent = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            parent = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + AppUtils.getAppName(null) + File.separator +
                    "record";
        } else {
            parent = HiUtils.getContext().getExternalFilesDir(null).getAbsolutePath() + File.separator + AppUtils.getAppName(null) + File.separator +
                    "record";
        }
        File father = new File(parent);
        if (!father.exists())
            father.mkdir();
        recordFile = new File(father, "" + System.currentTimeMillis() + ".wav");
        if (!recordFile.exists())
            try {
                recordFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private byte[] generateWAVHeader(AudioConfig config, long totalAudioLen) {
        return new WAVHeader(config, totalAudioLen).toBytes();
    }

    private void initAudioConfig() {
        this.audioConfig = new AudioConfig();
        this.audioConfig.setChannel(AudioFormat.CHANNEL_IN_MONO);
        this.audioConfig.setFrequency(AudioConfig.DEFAULT_SAMPLE_FREQUENCY);
        this.audioConfig.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.audioConfig.setAudioFormat(AudioFormat.ENCODING_PCM_16BIT);
    }
}
