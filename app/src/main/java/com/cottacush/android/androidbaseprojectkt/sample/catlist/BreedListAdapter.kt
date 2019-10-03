package com.cottacush.android.androidbaseprojectkt.sample.catlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cottacush.android.androidbaseprojectkt.R
import com.cottacush.android.androidbaseprojectkt.databinding.ItemCatListBinding
import com.cottacush.android.androidbaseprojectkt.sample.models.Breed

class BreedListAdapter(
    val breedClickListener: (Breed) -> Unit
) : ListAdapter<Breed, BreedListAdapter.CatBreedsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedsViewHolder {
        return CatBreedsViewHolder(
            ItemCatListBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_cat_list, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: CatBreedsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Breed>() {

        override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean = oldItem.id == newItem.id
    }

    inner class CatBreedsViewHolder(private var binding: ItemCatListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                breedClickListener(getItem(adapterPosition))
            }
        }

        fun bind(breed: Breed) {
            binding.breed = breed
            binding.executePendingBindings()
        }
    }
}
