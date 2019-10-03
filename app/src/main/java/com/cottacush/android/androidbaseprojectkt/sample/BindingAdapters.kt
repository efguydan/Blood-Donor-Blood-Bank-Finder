package com.cottacush.android.androidbaseprojectkt.sample

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cottacush.android.androidbaseprojectkt.sample.catlist.BreedListAdapter
import com.cottacush.android.androidbaseprojectkt.sample.models.Breed
import timber.log.Timber

@BindingAdapter("catBreedsList")
fun bindAccountsRecyclerView(recyclerView: RecyclerView, data: List<Breed>?) {
    data?.let {
        Timber.d("Update recycler view")
        val adapter = recyclerView.adapter as BreedListAdapter
        adapter.submitList(data)
    }
}
