package ru.ar4i.colorist.presentation.colors.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.ar4i.colorist.R
import ru.ar4i.colorist.data.entities.Color

class ColorsAdapter : RecyclerView.Adapter<ColorsViewHolder>() {

    private var items: List<Color> = mutableListOf()

    fun setData(items: List<Color>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        holder.bind(items.get(position))
    }


}