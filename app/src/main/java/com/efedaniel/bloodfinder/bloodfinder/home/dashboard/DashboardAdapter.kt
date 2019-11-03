package com.efedaniel.bloodfinder.bloodfinder.home.dashboard

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.databinding.ItemDashboardBinding
import android.R.attr.right
import android.R.attr.left
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints


class DashboardAdapter(
    private val clickListener: (String) -> Unit
): ListAdapter<String, DashboardAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDashboardBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }

    inner class ViewHolder(
        private var binding: ItemDashboardBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener(getItem(adapterPosition))
            }
        }

        fun bind(action: String) {
            binding.actionTextView.text = action
            binding.inProgressTextView.text = when(action) {
                "Logout" -> itemView.context.getString(R.string.logout_from_the_app)
                "Upload Blood Availability" -> itemView.context.getString(R.string.view_list_or_add_new)
                else -> itemView.context.getString(R.string.in_progress_check_back_shortly)
            }
        }

    }
}