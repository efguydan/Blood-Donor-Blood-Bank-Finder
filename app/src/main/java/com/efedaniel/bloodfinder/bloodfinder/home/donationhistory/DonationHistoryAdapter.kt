package com.efedaniel.bloodfinder.bloodfinder.home.donationhistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.databinding.ItemDonationHistoryBinding
import com.efedaniel.bloodfinder.extensions.getTime
import com.efedaniel.bloodfinder.extensions.hide
import com.efedaniel.bloodfinder.extensions.show
import com.efedaniel.bloodfinder.utils.ApiKeys

class DonationHistoryAdapter(
    private val clickListener: (BloodPostingRequest) -> Unit
) : ListAdapter<BloodPostingRequest, DonationHistoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDonationHistoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_donation_history, parent, false)
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
        private var binding: ItemDonationHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bloodPostingRequest: BloodPostingRequest) {
            binding.apply {
                bloodPosting = bloodPostingRequest
                executePendingBindings()
                creationTimeTextView.text = bloodPostingRequest.creationTime.getTime()
                statusTextView.text = bloodPostingRequest.status.capitalize()
                statusTextView.setTextColor(
                    ContextCompat.getColor(itemView.context, when (bloodPostingRequest.status) {
                        ApiKeys.ACCEPTED -> R.color.green
                        ApiKeys.PENDING -> R.color.yellow
                        else -> R.color.colorAccent
                    }))
                parentCardView.setOnClickListener { clickListener(bloodPostingRequest) }
                selectButton.apply {
                    if (bloodPostingRequest.status == ApiKeys.PENDING) {
                        show()
                        setOnClickListener { clickListener(bloodPostingRequest) }
                    } else {
                        hide()
                    }
                }
            }
        }
    }
}
