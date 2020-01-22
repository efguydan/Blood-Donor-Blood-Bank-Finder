package com.efedaniel.bloodfinder.bloodfinder.home.dashboard

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.R

import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentDashboardBinding
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import javax.inject.Inject

class DashboardFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DashboardViewModel::class.java)
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        binding.actionsRecyclerView.adapter = DashboardAdapter {
            when (it) {
                "Logout" -> logout()
                "Upload Blood Availability" -> navigateToBloodAvailability()
                "Request For Blood" -> navigateToBloodRequest()
                "View Profile" -> navigateToViewProfile()
                "View Request History" -> navigateToRequestHistory()
                "View Donation History" -> navigateToDonationHistory()
                else -> showSnackbar(it)
            }
        }
        binding.parentNestedScrollView.onScrollChanged {
            mainActivity.invalidateToolbarElevation(it)
        }
        viewModel.subscribeLoggedInUserToNotification()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dashboard_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() = findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSignInFragment())

    private fun navigateToBloodAvailability() = findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToBloodAvailabilityFragment())

    private fun navigateToBloodRequest() = findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToBloodRequestFragment())

    private fun navigateToViewProfile() = findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToViewProfileFragment())

    private fun navigateToRequestHistory() = findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToRequestHistoryFragment())

    private fun navigateToDonationHistory() = findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToDonationHistoryFragment())

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.dashboard), false)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
