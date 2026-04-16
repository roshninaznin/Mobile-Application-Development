package com.example.universityeventapp.data

import com.example.universityeventapp.R
import com.example.universityeventapp.model.Event

object EventData {

    fun getEvents(): ArrayList<Event> {
        return arrayListOf(

            Event(
                1,
                "Tech Summit",
                "20 Apr",
                "10:00 AM",
                "Auditorium",
                "Tech",
                "AI and future technology event",
                500.0,
                48,
                30,
                R.drawable.tech
            ),

            Event(
                2,
                "Sports Fest",
                "22 Apr",
                "9:00 AM",
                "Field",
                "Sports",
                "Annual sports competition",
                200.0,
                48,
                25,
                R.drawable.sports
            )

        )
    }
}