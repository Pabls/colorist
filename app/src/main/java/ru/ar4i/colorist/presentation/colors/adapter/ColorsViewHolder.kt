package ru.ar4i.colorist.presentation.colors.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ar4i.colorist.R
import ru.ar4i.colorist.data.entities.Color

class ColorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var vColor: View
    private lateinit var tvColorName: TextView

    init {
        vColor = view.findViewById(R.id.v_color)
        tvColorName = view.findViewById(R.id.tv_name)
    }

    fun bind(color: Color) {
        vColor.setBackgroundColor(android.graphics.Color.parseColor(color.hex))
        tvColorName.text = color.name
    }
}