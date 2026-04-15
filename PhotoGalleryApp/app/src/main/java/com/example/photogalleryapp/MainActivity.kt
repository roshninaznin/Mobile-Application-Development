package com.example.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var adapter: PhotoAdapter
    private lateinit var selectionBar: LinearLayout
    private lateinit var txtSelected: TextView

    private var photos = mutableListOf<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        selectionBar = findViewById(R.id.selectionBar)
        txtSelected = findViewById(R.id.txtSelected)

        loadPhotos()

        adapter = PhotoAdapter(photos,
            onClick = {
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image", it.resourceId)
                startActivity(intent)
            },
            onLongClick = {
                adapter.selectionMode = true
                selectionBar.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            }
        )

        gridView.adapter = adapter

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            val count = adapter.getSelectedCount()
            adapter.deleteSelected()
            Toast.makeText(this, "$count photos deleted", Toast.LENGTH_SHORT).show()
        }

        setupTabs()
    }

    private fun loadPhotos() {
        photos = mutableListOf(
            Photo(1, R.drawable.nature1, "Photo 1", "Nature"),
            Photo(2, R.drawable.travel1, "Photo 2", "Travel"),
            Photo(3, R.drawable.food1, "Photo 3", "Food"),
            Photo(4, R.drawable.animal1, "Photo 4", "Animals"),
            Photo(5, R.drawable.nature2, "Photo 5", "Nature"),
            Photo(6, R.drawable.animal2, "Photo 6", "Animals"),
            Photo(7, R.drawable.city1,   "Photo 7", "City"),
            Photo(8, R.drawable.travel2, "Photo 8", "Travel"),
            Photo(9, R.drawable.food2, "Photo 9", "Food"),
            Photo(10, R.drawable.animal3, "Photo 10", "Animals"),
            Photo(11, R.drawable.nature3, "Photo 11", "Nature"),
            Photo(12, R.drawable.travel3, "Photo 12", "Travel"),
            Photo(13, R.drawable.food3,   "Photo 13", "Food"),
            Photo(14, R.drawable.animal4, "Photo 14", "Animals"),
            Photo(15, R.drawable.city2,   "Photo 15", "City"),
            Photo(16, R.drawable.travel4, "Photo 16", "Travel"),
            Photo(17, R.drawable.food4,   "Photo 17", "Food"),
            Photo(18, R.drawable.food5,   "Photo 18", "Food"),
            Photo(19, R.drawable.animal5, "Photo 19", "Animals"),
            Photo(20, R.drawable.nature4, "Photo 20", "Nature"),
            Photo(21, R.drawable.animal6, "Photo 21", "Animals"),
            Photo(22, R.drawable.city3,   "Photo 22", "City"),
            Photo(23, R.drawable.animal7, "Photo 23", "Animals"),
            Photo(24, R.drawable.city4,   "Photo 24", "City"),
            Photo(25, R.drawable.nature5, "Photo 25", "Nature"),
            Photo(26, R.drawable.nature6, "Photo 26", "Nature"),
            Photo(27, R.drawable.city5,   "Photo 27", "City"),
            Photo(28, R.drawable.nature7, "Photo 28", "Nature"),
            Photo(29, R.drawable.city6,   "Photo 29", "City"),
            Photo(30, R.drawable.nature8, "Photo 30", "Nature"),
            Photo(31, R.drawable.city7,   "Photo 31", "City"),
            Photo(32, R.drawable.city8,   "Photo 32", "City")



            )
    }

    private fun setupTabs() {

        findViewById<Button>(R.id.tabAll).setOnClickListener {
            adapter.updateList(photos)
        }

        findViewById<Button>(R.id.tabNature).setOnClickListener {
            adapter.updateList(photos.filter { it.category == "Nature" }.toMutableList())
        }

        findViewById<Button>(R.id.tabCity).setOnClickListener {
            adapter.updateList(photos.filter { it.category == "City" }.toMutableList())
        }

        findViewById<Button>(R.id.tabAnimals).setOnClickListener {
            adapter.updateList(photos.filter { it.category == "Animals" }.toMutableList())
        }

        findViewById<Button>(R.id.tabFood).setOnClickListener {
            adapter.updateList(photos.filter { it.category == "Food" }.toMutableList())
        }

        findViewById<Button>(R.id.tabTravel).setOnClickListener {
            adapter.updateList(photos.filter { it.category == "Travel" }.toMutableList())
        }
    }
}