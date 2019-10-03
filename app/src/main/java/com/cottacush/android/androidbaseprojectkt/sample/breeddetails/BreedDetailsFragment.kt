package com.cottacush.android.androidbaseprojectkt.sample.breeddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cottacush.android.androidbaseprojectkt.base.BaseFragment
import com.cottacush.android.androidbaseprojectkt.databinding.FragmentBreedDetailBinding
import com.cottacush.android.androidbaseprojectkt.extensions.viewUrl

class BreedDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentBreedDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = BreedDetailsFragmentArgs.fromBundle(arguments!!)
        mainActivity.setUpToolBar("Breed Details")
        binding.breed = args.breed
        binding.viewDetailsButton.setOnClickListener {
            args.breed.wikipediaUrl?.let {
                mainActivity.viewUrl(it)
            }
        }
    }
}
