package com.example.fitnesstrackerapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var steps = 0
    val goal = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvSteps = findViewById<TextView>(R.id.tvSteps)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvPercent = findViewById<TextView>(R.id.tvPercent)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        btnUpdate.setOnClickListener {

            val input = EditText(this)

            AlertDialog.Builder(this)
                .setTitle("Update Steps")
                .setView(input)
                .setPositiveButton("Update") { _, _ ->

                    val value = input.text.toString().toIntOrNull() ?: 0
                    steps = value

                    val percent = (steps * 100) / goal

                    tvSteps.text = steps.toString()
                    progressBar.progress = percent
                    tvPercent.text = getString(R.string.percent_text, percent)
                    if (percent >= 100) {
                        Toast.makeText(
                            this,
                            "🎉 Goal Completed! Great Job!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}