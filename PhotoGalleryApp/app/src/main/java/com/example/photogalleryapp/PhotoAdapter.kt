package com.example.photogalleryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.BaseAdapter

class PhotoAdapter(
    private var photos: MutableList<Photo>,
    private val onClick: (Photo) -> Unit,
    private val onLongClick: (Photo) -> Unit
) : BaseAdapter() {

    var selectionMode = false

    override fun getCount() = photos.size
    override fun getItem(position: Int) = photos[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)

        val photo = photos[position]

        val img = view.findViewById<ImageView>(R.id.imgPhoto)
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val check = view.findViewById<CheckBox>(R.id.checkBox)

        img.setImageResource(photo.resourceId)
        title.text = photo.title

        check.visibility = if (selectionMode) View.VISIBLE else View.GONE
        check.isChecked = photo.isSelected

        view.setOnClickListener {
            if (selectionMode) {
                photo.isSelected = !photo.isSelected
                notifyDataSetChanged()
            } else {
                onClick(photo)
            }
        }

        view.setOnLongClickListener {
            onLongClick(photo)
            true
        }

        return view
    }

    fun updateList(newList: MutableList<Photo>) {
        photos = newList
        notifyDataSetChanged()
    }

    fun getSelectedCount(): Int {
        return photos.count { it.isSelected }
    }

    fun deleteSelected() {
        photos.removeAll { it.isSelected }
        notifyDataSetChanged()
    }
}