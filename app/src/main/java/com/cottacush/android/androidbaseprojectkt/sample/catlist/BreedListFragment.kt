package com.cottacush.android.androidbaseprojectkt.sample.catlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.cottacush.android.androidbaseprojectkt.App
import com.cottacush.android.androidbaseprojectkt.base.BaseFragment
import com.cottacush.android.androidbaseprojectkt.databinding.FragmentCatListsBinding
import com.cottacush.android.androidbaseprojectkt.networkutils.LoadingStatus
import javax.inject.Inject

class BreedListFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentCatListsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatListsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setUpToolBar("Breed List", true)
        (mainActivity.applicationContext as App).component.inject(this)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(BreedListViewModel::class.java)
        binding.viewModel = viewModel

//        binding.breedsRecyclerView.adapter = BreedListAdapter {
//            viewModel.displayCatBreedDetails(it)
//        }
//
//        viewModel.navigateToSelectedBreed.observe(this, Observer {
//            if (it != null) {
//                this.findNavController().navigate(
//                    BreedListFragmentDirections.actionCatsListFragmentToBreedDetailsFragment(it)
//                )
//                viewModel.displayCatBreedDetailsComplete()
//            }
//        })

        viewModel.loadingStatus.observe(this, Observer {
            when (it) {
                LoadingStatus.Success -> mainActivity.dismissLoading()
                is LoadingStatus.Loading -> mainActivity.showLoading(it.message)
                is LoadingStatus.Error -> mainActivity.showError(it.errorMessage)
            }
        })
    }
}
