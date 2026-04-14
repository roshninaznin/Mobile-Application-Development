package com.example.gradereportapp

import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var tableLayout: TableLayout
    lateinit var etSubject: EditText
    lateinit var etMarks: EditText
    lateinit var etTotal: EditText
    lateinit var tvGPA: TextView

    var totalPoints = 0.0
    var subjectCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        etSubject = findViewById(R.id.etSubject)
        etMarks = findViewById(R.id.etMarks)
        etTotal = findViewById(R.id.etTotal)
        tvGPA = findViewById(R.id.tvGPA)

        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {

            val subject = etSubject.text.toString()
            val marks = etMarks.text.toString().toIntOrNull() ?: 0
            val total = etTotal.text.toString().toIntOrNull() ?: 1

            val percent = (marks * 100) / total
            val grade = getGrade(percent)
            val point = getPoint(grade)

            totalPoints += point
            subjectCount++

            val row = TableRow(this)

            val color = if (grade == "F") Color.parseColor("#FFCDD2")
            else Color.parseColor("#C8E6C9")

            row.setBackgroundColor(color)

            row.addView(createText(subject))
            row.addView(createText("$marks"))
            row.addView(createText("$total"))
            row.addView(createText(grade))

            tableLayout.addView(row)

            val gpa = totalPoints / subjectCount
            tvGPA.text = "GPA: %.2f".format(gpa)

            etSubject.text.clear()
            etMarks.text.clear()
            etTotal.text.clear()
        }
    }

    private fun createText(text: String): TextView {
        val tv = TextView(this)
        tv.text = text
        tv.setPadding(8, 8, 8, 8)
        return tv
    }

    private fun getGrade(percent: Int): String {
        return when (percent) {
            in 90..100 -> "A+"
            in 80..89 -> "A"
            in 70..79 -> "B+"
            in 60..69 -> "B"
            in 50..59 -> "C"
            in 40..49 -> "D"
            else -> "F"
        }
    }

    private fun getPoint(grade: String): Double {
        return when (grade) {
            "A+" -> 4.0
            "A" -> 3.7
            "B+" -> 3.3
            "B" -> 3.0
            "C" -> 2.0
            "D" -> 1.0
            else -> 0.0
        }
    }
}