package com.efedaniel.bloodfinder.bloodfinder

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efedaniel.bloodfinder.bloodfinder.home.dashboard.DashboardAdapter

@BindingAdapter("quizList")
fun bindActionsRecyclerView(recyclerView: RecyclerView, data: List<String>?) {
    data?.let { (recyclerView.adapter as DashboardAdapter).submitList(data) }
}