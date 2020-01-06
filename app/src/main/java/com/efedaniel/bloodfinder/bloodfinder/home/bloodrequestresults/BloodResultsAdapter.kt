package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.bloodfinder.models.MiniLocation
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.databinding.ItemBloodResultBinding
import com.efedaniel.bloodfinder.extensions.convertToDistanceInKm
import com.efedaniel.bloodfinder.extensions.getTime

class BloodResultsAdapter(
    private val userLocation: MiniLocation,
    private val clickHandler: ClickHandler
) : ListAdapter<UploadBloodAvailabilityRequest, BloodResultsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBloodResultBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_blood_result, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    companion object DiffCallback : DiffUtil.ItemCallback<UploadBloodAvailabilityRequest>() {

        override fun areItemsTheSame(oldItem: UploadBloodAvailabilityRequest, newItem: UploadBloodAvailabilityRequest): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: UploadBloodAvailabilityRequest, newItem: UploadBloodAvailabilityRequest): Boolean = oldItem.bloodAvailabilityID == newItem.bloodAvailabilityID
    }

    inner class ViewHolder(
        private var binding: ItemBloodResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: UploadBloodAvailabilityRequest) {
            binding.bloodPosting = result
            binding.creationTimeTextView.text = result.creationTime.getTime()
            binding.distanceTextView.text = userLocation.distanceTo(result.location).convertToDistanceInKm()
            binding.phoneButton.setOnClickListener { clickHandler.call(getItem(adapterPosition).donorPhoneNumber) }
            binding.selectButton.setOnClickListener { clickHandler.showBloodPostingFullDetails(getItem(adapterPosition)) }
            binding.layoutCardView.setOnClickListener { clickHandler.showBloodPostingFullDetails(getItem(adapterPosition)) }
        }
    }

    interface ClickHandler {
        fun call(phoneNUmber: String)

        fun showBloodPostingFullDetails(posting: UploadBloodAvailabilityRequest)
    }
}
