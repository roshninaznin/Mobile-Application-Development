package com.example.ecommerceapp.adapter

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*
import com.example.ecommerceapp.R
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.util.ProductDiff

class ProductAdapter(
    private var fullList: MutableList<Product>,
    private val onCartClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = fullList.toMutableList()
    var isGrid = false

    companion object {
        const val LIST = 1
        const val GRID = 2
    }

    override fun getItemViewType(position: Int) = if (isGrid) GRID else LIST

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)

        return if (viewType == LIST) {
            ListVH(view.inflate(R.layout.item_product_list, parent, false))
        } else {
            GridVH(view.inflate(R.layout.item_product_grid, parent, false))
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if (holder is ListVH) holder.bind(item)
        if (holder is GridVH) holder.bind(item)
    }

    inner class ListVH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.tvName)
        val price: TextView = v.findViewById(R.id.tvPrice)
        val rating: RatingBar = v.findViewById(R.id.ratingBar)
        val category: TextView = v.findViewById(R.id.tvCategory)
        val image: ImageView = v.findViewById(R.id.ivProduct)
        val btn: Button = v.findViewById(R.id.btnCart)

        fun bind(p: Product) {
            name.text = p.name
            price.text = itemView.context.getString(
                R.string.price_format,
                p.price
            )
            rating.rating = p.rating
            category.text = p.category
            image.setImageResource(p.imageRes)

            btn.text = if (p.inCart) "Remove" else "Add"
            btn.setOnClickListener { onCartClick(p) }
        }
    }

    inner class GridVH(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = v.findViewById(R.id.tvName)
        val price: TextView = v.findViewById(R.id.tvPrice)
        val image: ImageView = v.findViewById(R.id.ivProduct)
        val btn: ImageButton = v.findViewById(R.id.btnCart)

        fun bind(p: Product) {
            name.text = p.name
            price.text = itemView.context.getString(
                R.string.price_format,
                p.price
            )
            image.setImageResource(p.imageRes)

            btn.setOnClickListener { onCartClick(p) }
        }
    }

    fun filter(query: String, category: String) {
        val newList = fullList.filter {
            it.name.contains(query, true) &&
                    (category == "All" || it.category == category)
        }

        val diff = DiffUtil.calculateDiff(ProductDiff(list, newList))
        list = newList.toMutableList()
        diff.dispatchUpdatesTo(this)
    }

    fun removeAt(pos: Int): Product {
        val item = list[pos]
        list.removeAt(pos)
        fullList.remove(item)
        notifyItemRemoved(pos)
        return item
    }

    fun restore(item: Product, pos: Int) {
        list.add(pos, item)
        fullList.add(item)
        notifyItemInserted(pos)
    }

    fun moveItem(from: Int, to: Int) {
        val item = list.removeAt(from)
        list.add(to, item)
        notifyItemMoved(from, to)
    }
}