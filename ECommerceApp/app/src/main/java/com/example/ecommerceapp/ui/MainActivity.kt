package com.example.ecommerceapp.ui

import android.content.Intent
import android.os.*
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.*
import com.example.ecommerceapp.R
import com.example.ecommerceapp.adapter.ProductAdapter
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.util.CartManager
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var emptyView: TextView

    private var query = ""
    private var category = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        recycler = findViewById(R.id.recyclerView)
        emptyView = findViewById(R.id.tvEmpty)

        val list = mutableListOf(
            Product(1, "Phone", 500.0, 4.5f, "Electronics", R.drawable.ic_launcher_foreground),
            Product(2, "Shirt", 30.0, 4f, "Clothing", R.drawable.ic_launcher_foreground),
            Product(3, "Book", 20.0, 5f, "Books", R.drawable.ic_launcher_foreground)
        )

        adapter = ProductAdapter(list) { product ->
            if (product.inCart) {
                product.inCart = false
                CartManager.remove(product)
            } else {
                product.inCart = true
                CartManager.add(product)
            }
            val index = list.indexOf(product)
            if (index != -1) {
                adapter.notifyItemChanged(index)
            }

            invalidateOptionsMenu()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        setupChips()
        setupSwipe()
        updateEmpty()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        menu.findItem(R.id.menu_cart).title = "Cart (${CartManager.count()})"

        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?) = false
            override fun onQueryTextChange(text: String?): Boolean {
                query = text ?: ""
                adapter.filter(query, category)
                updateEmpty()
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_toggle -> {
                adapter.isGrid = !adapter.isGrid
                recycler.layoutManager =
                    if (adapter.isGrid) GridLayoutManager(this, 2)
                    else LinearLayoutManager(this)
                recycler.adapter = adapter
            }

            R.id.menu_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
            }
        }
        return true
    }

    private fun setupChips() {
        val chipGroup = findViewById<com.google.android.material.chip.ChipGroup>(R.id.chipGroup)

        listOf("All","Electronics","Clothing","Books","Food","Toys").forEach { item ->

            val chip = Chip(this)
            chip.text = item

            chip.setOnClickListener {
                category = item
                adapter.filter(query, category)
                updateEmpty()
            }

            chipGroup.addView(chip)
        }
    }

    private fun setupSwipe() {
        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {

            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder): Boolean {
                adapter.moveItem(vh.bindingAdapterPosition, t.bindingAdapterPosition)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val pos = vh.bindingAdapterPosition
                val item = adapter.removeAt(pos)

                com.google.android.material.snackbar.Snackbar
                    .make(recycler, "Deleted", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        adapter.restore(item, pos)
                    }.show()

                updateEmpty()
            }
        })

        helper.attachToRecyclerView(recycler)
    }

    private fun updateEmpty() {
        if (adapter.itemCount == 0) {
            emptyView.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            recycler.visibility = View.VISIBLE
        }
    }
}