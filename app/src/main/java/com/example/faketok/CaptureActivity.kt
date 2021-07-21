package com.example.faketok

import android.os.Bundle
import android.view.SurfaceView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.faketok.util.*;

class CaptureActivity : AppCompatActivity() {

    private lateinit var video: SurfaceView
    private lateinit var controler: Button
    private lateinit var videoRecorder: VideoRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        video=findViewById(R.id.videoRecorder)
        controler=findViewById(R.id.VideoController)
        videoRecorder= VideoRecorder()
        controler.setOnClickListener{startRecording()}
    }

    private fun startRecording() {
        if (!videoRecorder?.isRecording){
            videoRecorder?.startRecording(video,this)
            controler.setText(R.string.endVideo)
        }else{
            videoRecorder?.stopRecording()
            controler.setText(R.string.startVideo)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoRecorder?.stopRecording()
    }

}