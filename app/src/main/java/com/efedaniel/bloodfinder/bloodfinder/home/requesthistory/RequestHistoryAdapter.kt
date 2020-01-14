package com.efedaniel.bloodfinder.bloodfinder.home.requesthistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.databinding.ItemRequestHistoryBinding
import com.efedaniel.bloodfinder.extensions.getTime
import com.efedaniel.bloodfinder.utils.ApiKeys

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
            binding.apply {
                bloodPosting = bloodPostingRequest
                executePendingBindings()
                creationTimeTextView.text = bloodPostingRequest.creationTime.getTime()
                statusTextView.text = bloodPostingRequest.status.capitalize()
                statusTextView.setTextColor(ContextCompat.getColor(itemView.context, when(bloodPostingRequest.status) {
                    ApiKeys.ACCEPTED -> R.color.green
                    ApiKeys.PENDING -> R.color.yellow
                    else -> R.color.colorAccent
                }))
            }
        }
    }

}