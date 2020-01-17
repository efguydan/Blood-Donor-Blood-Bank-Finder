package com.efedaniel.bloodfinder.bloodfinder

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty.BloodAvailabilityAdapter
import com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults.BloodResultsAdapter
import com.efedaniel.bloodfinder.bloodfinder.home.dashboard.DashboardAdapter
import com.efedaniel.bloodfinder.bloodfinder.home.donationhistory.DonationHistoryAdapter
import com.efedaniel.bloodfinder.bloodfinder.home.requesthistory.RequestHistoryAdapter
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest

@BindingAdapter("quizList")
fun bindActionsRecyclerView(recyclerView: RecyclerView, data: List<String>?) {
    data?.let { (recyclerView.adapter as DashboardAdapter).submitList(data) }
}

@BindingAdapter("bloodAvailabilityList")
fun bindBloodAvailabilityRecyclerView(recyclerView: RecyclerView, data: List<UploadBloodAvailabilityRequest>?) {
    data?.let { (recyclerView.adapter as BloodAvailabilityAdapter).submitList(data) }
}

@BindingAdapter("bloodResultList")
fun bindBloodResultRecyclerView(recyclerView: RecyclerView, data: List<UploadBloodAvailabilityRequest>?) {
    data?.let { (recyclerView.adapter as BloodResultsAdapter).submitList(data) }
}

@BindingAdapter("requestHistoryList")
fun bindRequestHistoryRecyclerView(recyclerView: RecyclerView, data: List<BloodPostingRequest>?) {
    data?.let { (recyclerView.adapter as RequestHistoryAdapter).submitList(data) }
}

@BindingAdapter("donationHistoryList")
fun bindDonationHistoryRecyclerView(recyclerView: RecyclerView, data: List<BloodPostingRequest>?) {
    data?.let { (recyclerView.adapter as DonationHistoryAdapter).submitList(data) }
}
