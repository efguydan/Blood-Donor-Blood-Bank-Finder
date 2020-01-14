package com.efedaniel.bloodfinder.bloodfinder.home.requesthistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.databinding.ItemRequestHistoryBinding

class RequestHistoryAdapter : ListAdapter<BloodPostingRequest, RequestHistoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRequestHistoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_request_history, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<BloodPostingRequest>() {
        override fun areItemsTheSame(
            oldItem: BloodPostingRequest,
            newItem: BloodPostingRequest
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: BloodPostingRequest,
            newItem: BloodPostingRequest
        ): Boolean = oldItem.bloodRequestID == newItem.bloodRequestID
    }

    inner class ViewHolder(
        private var binding: ItemRequestHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bloodPostingRequest: BloodPostingRequest) {
            binding.bloodPosting = bloodPostingRequest
            binding.executePendingBindings()
        }
    }

}