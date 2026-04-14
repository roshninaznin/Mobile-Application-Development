package com.example.contactbookapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var searchView: SearchView
    lateinit var tvEmpty: TextView

    val contacts = mutableListOf<Contact>()
    lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        searchView = findViewById(R.id.searchView)
        tvEmpty = findViewById(R.id.tvEmpty)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        adapter = ContactAdapter(this, contacts)
        listView.adapter = adapter

        // ADD CONTACT
        fab.setOnClickListener {

            val etName = EditText(this)
            etName.hint = "Name"

            val etPhone = EditText(this)
            etPhone.hint = "Phone"

            val etEmail = EditText(this)
            etEmail.hint = "Email"

            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(20, 20, 20, 20)

            layout.addView(etName)
            layout.addView(etPhone)
            layout.addView(etEmail)

            AlertDialog.Builder(this)
                .setTitle("Add Contact")
                .setView(layout)
                .setPositiveButton("Add") { _, _ ->

                    val name = etName.text.toString()
                    val phone = etPhone.text.toString()
                    val email = etEmail.text.toString()

                    if (name.isEmpty() || phone.isEmpty()) {
                        Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    contacts.add(
                        Contact(name, phone, email, name.first().uppercase())
                    )

                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // CLICK
        listView.setOnItemClickListener { _, _, pos, _ ->
            val c = contacts[pos]
            Toast.makeText(this,
                "${c.name} - ${c.phone} - ${c.email}",
                Toast.LENGTH_LONG).show()
        }

        // DELETE
        listView.setOnItemLongClickListener { _, _, pos, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete Contact?")
                .setPositiveButton("Yes") { _, _ ->
                    contacts.removeAt(pos)
                    adapter.notifyDataSetChanged()
                }
                .setNegativeButton("No", null)
                .show()
            true
        }

        // SEARCH
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {

                val filtered = contacts.filter {
                    it.name.contains(newText ?: "", true)
                }

                adapter.clear()
                adapter.addAll(filtered)

                tvEmpty.visibility =
                    if (filtered.isEmpty()) View.VISIBLE else View.GONE

                return true
            }
        })
    }
}