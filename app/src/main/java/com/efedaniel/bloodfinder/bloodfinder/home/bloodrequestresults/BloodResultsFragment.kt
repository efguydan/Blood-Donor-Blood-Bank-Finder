package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentBloodResultsBinding
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import javax.inject.Inject

class BloodResultsFragment : BaseFragment(), BloodResultsAdapter.ClickHandler {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBloodResultsBinding
    private lateinit var viewModel: BloodResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBloodResultsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BloodResultsViewModel::class.java)
        binding.viewModel = viewModel
        binding.numberOfResultTextView.text = String.format(getString(R.string.amount_of_blood_results_found_format),
            BloodResultsFragmentArgs.fromBundle(arguments!!).bloodResultsList.size)
        binding.resultsRecyclerView.adapter = BloodResultsAdapter(this)
        viewModel.setBloodResults(BloodResultsFragmentArgs.fromBundle(arguments!!).bloodResultsList.toList())
        binding.parentLayout.onScrollChanged { mainActivity.invalidateToolbarElevation(it) }
    }

    override fun call(phoneNUmber: String) {
        val phoneIntent = Intent(Intent.ACTION_CALL)
        phoneIntent.data = Uri.parse("tel:$phoneNUmber")
        startActivity(phoneIntent)
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_search_results), true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
