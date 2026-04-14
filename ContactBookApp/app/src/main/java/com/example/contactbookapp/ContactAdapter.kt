package com.example.contactbookapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(
    context: Context,
    private val list: MutableList<Contact>
) : ArrayAdapter<Contact>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contact, parent, false)

        val contact = list[position]

        val tvInitial = view.findViewById<TextView>(R.id.tvInitial)
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvPhone = view.findViewById<TextView>(R.id.tvPhone)

        tvInitial.text = contact.initial
        tvName.text = contact.name
        tvPhone.text = contact.phone

        return view
    }
}