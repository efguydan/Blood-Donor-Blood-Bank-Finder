package com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.databinding.ItemBloodAvailabilityBinding
import com.efedaniel.bloodfinder.extensions.getTime

class BloodAvailabilityAdapter(

): ListAdapter<UploadBloodAvailabilityRequest, BloodAvailabilityAdapter.ViewHolder>(DiffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBloodAvailabilityBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_blood_availability, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))


    companion object DiffCallback : DiffUtil.ItemCallback<UploadBloodAvailabilityRequest>() {

        override fun areItemsTheSame(oldItem: UploadBloodAvailabilityRequest, newItem: UploadBloodAvailabilityRequest): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: UploadBloodAvailabilityRequest, newItem: UploadBloodAvailabilityRequest): Boolean = oldItem.bloodAvailabilityID == newItem.bloodAvailabilityID
    }

    inner class ViewHolder(
        private var binding: ItemBloodAvailabilityBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(availabilityRequest: UploadBloodAvailabilityRequest) {
            binding.bloodPosting = availabilityRequest
            binding.creationTimeTextView.text = availabilityRequest.creationTime.getTime()
        }
    }

}