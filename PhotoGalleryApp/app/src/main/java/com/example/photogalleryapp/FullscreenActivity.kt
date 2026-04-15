package com.example.photogalleryapp

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FullscreenActivity : AppCompatActivity() {

    private var scale = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        val imageView = findViewById<ImageView>(R.id.fullImage)

        val imgRes = intent.getIntExtra("image", 0)
        imageView.setImageResource(imgRes)

        imageView.setOnTouchListener { _, event ->
            if (event.pointerCount == 2) {
                scale += 0.01f
                imageView.scaleX = scale
                imageView.scaleY = scale
            }
            true
        }
    }
}