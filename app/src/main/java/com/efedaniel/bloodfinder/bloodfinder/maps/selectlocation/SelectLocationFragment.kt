package com.efedaniel.bloodfinder.bloodfinder.maps.selectlocation


import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.base.BaseFragment
import com.efedaniel.bloodfinder.base.BaseViewModel
import com.efedaniel.bloodfinder.databinding.FragmentSelectLocationBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class SelectLocationFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentSelectLocationBinding
    private lateinit var viewModel: SelectLocationViewModel
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectLocationBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectLocationViewModel::class.java)
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        binding.selectLocationButton.setOnClickListener {
            findNavController().navigate(SelectLocationFragmentDirections.actionSelectLocationFragmentToDashboardFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.select_location_menu, menu)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        //Show Dialog to alert users to select their location
        showDialogWithAction(body = getString(R.string.move_map_around_message), positiveRes = R.string.close, cancelOnTouchOutside = true)

        //TODO move map to users current location by default

        // Add a marker in Nigeria and move the camera
        val nigeria = LatLng(9.08, 8.67)
//        map.addMarker(MarkerOptions().position(nigeria).title("Marker in Nigeria"))
        map.moveCamera(CameraUpdateFactory.newLatLng(nigeria))
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.select_location), false)
        invalidateToolbarElevation(4)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
