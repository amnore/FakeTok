package com.example.faketok;

import android.Manifest;
import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class VideoRecorder {
    private MediaRecorder mediaRecorder;
    private boolean isRecording=false;
    private Timer timer=new Timer();
    private String fileName;
    private Integer seconds=0;

    public boolean isRecording() {
        return isRecording;
    }

    public void startRecording(SurfaceView surfaceView, CaptureActivity activity){
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO}, 1);
        if (mediaRecorder!=null) mediaRecorder.reset();
        else mediaRecorder=new MediaRecorder();
        //设置录制方向，竖屏时默认是横屏方向(坑)
        Camera camera=Camera.open(0);
        camera.setDisplayOrientation(90);
        camera.unlock();
        mediaRecorder.setCamera(camera);

        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setVideoFrameRate(20);
        //设置分辨率
//        Camera.Parameters parameter= camera.getParameters();
//        List<Camera.Size> prviewSizeList = parameter.getSupportedPreviewSizes();
//        List<Camera.Size> videoSizeList = parameter.getSupportedVideoSizes();
//        parameter.setPreviewSize(prviewSizeList.get(0).width,prviewSizeList.get(0).height);

        mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
        fileName=getFileName();
        System.out.println(fileName);
        System.out.println(new File(fileName).getAbsolutePath());
        if (fileName!=null) {
            mediaRecorder.setOutputFile(fileName);
        }
        try{
            mediaRecorder.prepare();
            mediaRecorder.start();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        isRecording=true;
        seconds=0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                //todo:更新计时器
            }
        }, 0,1000);
    }
    public void stopRecording(){
        isRecording=false;
        mediaRecorder.stop();
        mediaRecorder.release();
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (IllegalStateException e) {
                //e.printStackTrace();
                mediaRecorder = null;
                mediaRecorder = new MediaRecorder();
            }
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
    private String getFileName() {
        try {
            return File.createTempFile("Vedio"+ System.currentTimeMillis(), ".mp4").getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
