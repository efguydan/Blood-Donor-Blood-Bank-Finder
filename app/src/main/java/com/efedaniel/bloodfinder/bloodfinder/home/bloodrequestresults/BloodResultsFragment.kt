package com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App

import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.bloodfinder.models.request.UploadBloodAvailabilityRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.databinding.FragmentBloodResultsBinding
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import com.efedaniel.bloodfinder.utils.Misc
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import javax.inject.Inject

class BloodResultsFragment : BaseFragment(), BloodResultsAdapter.ClickHandler {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var prefsUtils: PrefsUtils

    private lateinit var binding: FragmentBloodResultsBinding
    private lateinit var viewModel: BloodResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

        val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)
        binding.resultsRecyclerView.adapter = BloodResultsAdapter(user.location!!, this)

        viewModel.setBloodResults(BloodResultsFragmentArgs.fromBundle(arguments!!).bloodResultsList.toList())
        binding.parentLayout.onScrollChanged { mainActivity.invalidateToolbarElevation(it) }
    }

    override fun call(phoneNUmber: String) {
        val phoneIntent = Intent(Intent.ACTION_CALL)
        phoneIntent.data = Uri.parse("tel:$phoneNUmber")
        if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(phoneIntent)
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), Misc.CALL_PERMISSION_REQUEST)
        }
    }

    override fun showBloodPostingFullDetails(posting: UploadBloodAvailabilityRequest) {
        findNavController().navigate(BloodResultsFragmentDirections.actionBloodResultsFragmentToBloodPostingDetailsFragment(posting))
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.blood_search_results), true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
