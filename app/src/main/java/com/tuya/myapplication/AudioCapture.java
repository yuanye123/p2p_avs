package com.tuya.myapplication;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.tuya.api.AvsJniApi;

public class AudioCapture {

    private AudioRecord audioRecord;
    //只是推送数据流
    private boolean isAudioPush = false;

    private int pcmBufferSize;
    private byte[] pcmBuffer;


    public AudioCapture() {

        float captureInterval = 1.0f / 25;
        pcmBufferSize = (int )(8000 * 16 * 1 * captureInterval) / 8;
        //必须要有对应的权限 Manifest.permission.RECORD_AUDIO
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                8000, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, pcmBufferSize);
        pcmBuffer = new byte[pcmBufferSize];
    }

    public void startCapture() {
        if (audioRecord == null) {
            return;
        }
        if (!isAudioPush) {
            isAudioPush = true;
        }
        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_STOPPED) {
            audioRecord.startRecording();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isAudioPush && audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                        int len = audioRecord.read(pcmBuffer, 0, pcmBuffer.length);
                        if (len > 0) {
                            //trans audio stream
                            AvsJniApi.INSTANCE.native_feedPcm_asr(pcmBuffer);
                        }
                    }
                }
            }).start();
        }
    }

    public void stopCapture() {
        audioRecord.stop();
        isAudioPush = false;
    }





}
