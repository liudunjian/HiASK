package com.hisense.hiask.hiaudio.record;

import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.HandlerThread;

import com.hisense.hiask.hiaudio.manager.AudioConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by liudunjian on 2018/5/15.
 */

public class PCMRecorder {

    private AudioRecord audioRecord;
    private AudioConfig audioConfig = null;
    private int bufferSize;

    private Handler handler;

    public PCMRecorder(AudioConfig audioConfig) {
        this.audioConfig = audioConfig;
        HandlerThread ht = new HandlerThread("recording thread", Thread.NORM_PRIORITY);
        ht.start();
        handler = new Handler(ht.getLooper());
    }

    public void startRecord(File file, IRecordingRMSListener rmsListener) throws Exception {
        if (this.audioRecord == null || this.audioRecord.getState() != AudioRecord.STATE_INITIALIZED)
            this.initAudioRecord();
        audioRecord.startRecording();
        AudioBufferedStream audioBufferedStream = new AudioBufferedStream(audioRecord, new FileOutputStream(file));
        audioBufferedStream.setRmsListener(rmsListener);
        handler.post(audioBufferedStream);
    }

    public void finishRecord() {
        if (audioRecord != null && this.audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING)
            this.audioRecord.stop();
    }

    public void releaseRecord() {
        if (audioRecord != null) {
            if(audioRecord.getState()==AudioRecord.STATE_INITIALIZED) {
                audioRecord.stop();
                audioRecord.release();
            }
            audioRecord = null;
        }
    }

    private void initAudioRecord() throws Exception {

        bufferSize = AudioRecord.getMinBufferSize(audioConfig.getFrequency(), audioConfig.getChannel(), audioConfig.getAudioFormat());
        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            throw new Exception("本设备不支持该声音采样设置");
        }
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, audioConfig.getFrequency(), audioConfig.getChannel(), audioConfig.getAudioFormat(), bufferSize);
        if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED)
            throw new Exception("录音设备不能初始化");
    }

    private static class AudioBufferedStream implements Runnable {

        private AudioRecord recorder;
        private FileOutputStream recordFile;
        private IRecordingRMSListener rmsListener;

        public AudioBufferedStream(AudioRecord recorder, FileOutputStream file) {
            this.recorder = recorder;
            this.recordFile = file;
        }

        public IRecordingRMSListener getRmsListener() {
            return rmsListener;
        }

        public void setRmsListener(IRecordingRMSListener rmsListener) {
            this.rmsListener = rmsListener;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[4096];
            int size = 0;
            while (this.recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                while ((size = recorder.read(buffer, 0, buffer.length)) > 0) {
                    try {
                        calculateDB(buffer, size);
                        recordFile.write(buffer, 0, size);
                    } catch (IOException e) {
                        this.recorder.stop();
                    }
                }
            }
            closeAudioBufferedFile();
        }

        private void closeAudioBufferedFile() {
            try {
                recordFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // rmsListener is the AudioRMSListener callback for audio visualizer(optional - can be null)
        // assuming 16bit samples, 1 channel, little endian
        private void calculateDB(byte[] data, int cnt) {
            if ((rmsListener == null) || (cnt < 2)) {
                return;
            }

            final int bytesPerSample = 2;
            int len = cnt / bytesPerSample;
            double avg = 0;

            for (int i = 0; i < cnt; i += bytesPerSample) {
                ByteBuffer bb = ByteBuffer.allocate(bytesPerSample);
                bb.order(ByteOrder.LITTLE_ENDIAN);
                bb.put(data[i]);
                bb.put(data[i + 1]);
                // generate the signed 16 bit number from the 2 bytes
                double dVal = java.lang.Math.abs(bb.getShort(0));
                // scale it from 1 to 100. Use max/2 as values tend to be low
                dVal = ((100 * dVal) / (Short.MAX_VALUE / 2.0)) + 1;
                avg += dVal * dVal; // add the square to the running average
            }
            avg /= len;
            avg = java.lang.Math.sqrt(avg);
            // update the AudioRMSListener callback with the scaled root-mean-squared power value
            if (rmsListener != null)
                rmsListener.rmsChanged((int) avg);
        }
    }


}
