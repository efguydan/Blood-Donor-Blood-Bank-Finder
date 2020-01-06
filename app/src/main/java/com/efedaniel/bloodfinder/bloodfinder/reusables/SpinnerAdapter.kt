package com.efedaniel.bloodfinder.bloodfinder.reusables

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.efedaniel.bloodfinder.R

class SpinnerAdapter(
    private val context: Context,
    private val userTypes: List<String>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_drop_down, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }
        if (position == 0) vh.label.setTextColor(Color.GRAY)
        vh.label.text = userTypes[position]
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_drop_down, parent, false)
        val vh = ItemRowHolder(view)
        vh.label.text = userTypes[position]
        vh.label.setTextColor(if (position == 0) Color.GRAY else Color.BLACK)
        return view
    }

    override fun isEnabled(position: Int) = position > 0

    override fun getItem(position: Int) = userTypes[position]

    override fun getItemId(position: Int) = 0L

    override fun getCount() = userTypes.size

    private class ItemRowHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.txtDropDownLabel) as TextView
    }
}
