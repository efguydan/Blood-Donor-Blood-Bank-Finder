package com.efedaniel.bloodfinder.bloodfinder.home.donationhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentDonationHistoryBinding
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import com.efedaniel.bloodfinder.extensions.show
import com.efedaniel.bloodfinder.utils.ApiKeys
import kotlinx.android.synthetic.main.empty_layout.view.*
import javax.inject.Inject

class DonationHistoryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentDonationHistoryBinding
    private lateinit var viewModel: DonationHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonationHistoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DonationHistoryViewModel::class.java)
        binding.viewModel = viewModel

        binding.donationHistoryRecyclerView.apply {
            adapter = DonationHistoryAdapter {
                if (it.status == ApiKeys.PENDING) {
                    findNavController().navigate(DonationHistoryFragmentDirections.actionDonationHistoryFragmentToBloodPostingRequestFragment(it))
                }
            }
            onScrollChanged { mainActivity.invalidateToolbarElevation(it) }
        }
        viewModel.getUserDonationHistory()
        viewModel.emptyViewVisibility.observe(this, Observer {
            if (it) {
                binding.emptyView.show()
                binding.emptyView.emptyTextTitle.text = getString(R.string.empty_donation_history)
                binding.emptyView.emptyTextDescription.text = getString(R.string.empty_donation_history_description)
            }
        })
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.donation_history), true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
