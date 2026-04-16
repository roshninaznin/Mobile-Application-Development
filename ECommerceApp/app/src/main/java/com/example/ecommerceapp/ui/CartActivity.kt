package com.example.ecommerceapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.util.CartManager

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val recycler = findViewById<RecyclerView>(R.id.cartRecycler)
        val total = findViewById<TextView>(R.id.tvTotal)
        val btn = findViewById<Button>(R.id.btnCheckout)

        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = object : RecyclerView.Adapter<SimpleVH>() {

            override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): SimpleVH {
                val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
                return SimpleVH(view)
            }

            override fun getItemCount() = CartManager.getItems().size

            override fun onBindViewHolder(holder: SimpleVH, position: Int) {
                val item = CartManager.getItems()[position]
                holder.text.text = holder.itemView.context.getString(
                    R.string.cart_item_format,
                    item.name,
                    item.price
                )
            }
        }

        fun updateTotal() {
            total.text = getString(R.string.total_format, CartManager.total())        }

        updateTotal()

        btn.setOnClickListener {
            CartManager.clear()
            recycler.adapter = recycler.adapter
            updateTotal()
        }
    }

    class SimpleVH(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(android.R.id.text1)
    }
}