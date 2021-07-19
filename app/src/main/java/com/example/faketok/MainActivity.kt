package com.example.faketok

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.faketok.util.Constant

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var fullscreenContent: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        fullscreenContent = findViewById(R.id.fullscreen_content)
        fullscreenContent.setOnClickListener { toggle() }

        Log.d(Constant.APP, "MainActivity, onCreate finishes")
    }

    private fun toggle() {
        if (supportActionBar?.isShowing == true) {
            supportActionBar?.hide()
            Log.d(Constant.APP, "MainActivity, now the action bar is hidden")
        } else {
            supportActionBar?.show()
            Log.d(Constant.APP, "MainActivity, now the action bar shows up")
        }
    }

}