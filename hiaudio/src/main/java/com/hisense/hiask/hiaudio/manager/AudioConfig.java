package com.hisense.hiask.hiaudio.manager;

import android.media.AudioFormat;

/**
 * Created by liudunjian on 2018/5/14.
 */

public class AudioConfig {

    public static final int DEFAULT_SAMPLE_FREQUENCY = 16000;

    private int channel;
    private int frequency;
    private int audioSource;
    private int audioFormat;

    public AudioConfig() {

    }

    public AudioConfig(int audioSource, int audioFormat,int channel, int frequency) {
        this.channel = channel;
        this.frequency = frequency;
        this.audioSource = audioSource;
        this.audioFormat = audioFormat;
    }

    public long byteRate(){
        return this.bitsPerSample() * frequency * this.bitsChannels() / 8;
    }
    public byte bitsChannels(){
        if(this.channel == AudioFormat.CHANNEL_IN_MONO){
            return 1;
        }
        return 2;
    }
    public byte bitsPerSample() {
        if (this.audioFormat == AudioFormat.ENCODING_PCM_16BIT) {
            return 16;
        } else if (this.audioFormat == AudioFormat.ENCODING_PCM_8BIT) {
            return 8;
        } else {
            return 16;
        }
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(int audioFormat) {
        this.audioFormat = audioFormat;
    }
}
