package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView


class MainActivity : AppCompatActivity() {

    var bookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val scroll = findViewById<NestedScrollView>(R.id.scrollView)
        val btnTop = findViewById<Button>(R.id.btnTop)
        val btnBookmark = findViewById<ImageView>(R.id.btnBookmark)
        val btnShare = findViewById<ImageView>(R.id.btnShare)

        val intro = findViewById<TextView>(R.id.sectionIntro)
        val key = findViewById<TextView>(R.id.sectionKey)
        val analysis = findViewById<TextView>(R.id.sectionAnalysis)
        val conclusion = findViewById<TextView>(R.id.sectionConclusion)

        findViewById<Button>(R.id.btnIntro).setOnClickListener {
            scroll.smoothScrollTo(0, intro.top)
        }

        findViewById<Button>(R.id.btnKey).setOnClickListener {
            scroll.smoothScrollTo(0, key.top)
        }

        findViewById<Button>(R.id.btnAnalysis).setOnClickListener {
            scroll.smoothScrollTo(0, analysis.top)
        }

        findViewById<Button>(R.id.btnConclusion).setOnClickListener {
            scroll.smoothScrollTo(0, conclusion.top)
        }

        btnTop.setOnClickListener {
            scroll.smoothScrollTo(0, 0)
        }

        // Bookmark
        btnBookmark.setOnClickListener {
            bookmarked = !bookmarked

            if (bookmarked) {
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                btnBookmark.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        // Share
        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "AI is Changing the Future")
            startActivity(Intent.createChooser(intent, "Share via"))
        }
    }
}