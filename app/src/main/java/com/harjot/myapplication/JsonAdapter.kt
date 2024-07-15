package com.harjot.myapplication

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class JsonAdapter(var array: ArrayList<JsonModel>,var jsonInterface: JsonInterface) : RecyclerView.Adapter<JsonAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvName)
        var image: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val initView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(initView)
    }

    override fun getItemCount(): Int = array.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = array[position].name
        holder.itemView.setOnClickListener {
            jsonInterface.onListClick(position)
        }
        try {
            val inputStream = holder.itemView.context.assets.open(array[position].image)
            val drawable = Drawable.createFromStream(inputStream, null)
            holder.image.setImageDrawable(drawable)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("JsonAdapter", "Error loading image: ${array[position].image}")
            holder.image.setImageResource(R.drawable.abc) // Set a default image in case of error
        }
    }
}
