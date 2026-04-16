package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityeventapp.data.EventData

class EventsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val events = EventData.getEvents()

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = EventAdapter(events) { event ->
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }
    }
}