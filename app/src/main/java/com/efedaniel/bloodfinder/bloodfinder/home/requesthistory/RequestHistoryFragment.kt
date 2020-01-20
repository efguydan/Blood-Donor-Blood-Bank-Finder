package com.efedaniel.bloodfinder.bloodfinder.home.requesthistory

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
import com.efedaniel.bloodfinder.databinding.FragmentRequestHistoryBinding
import com.efedaniel.bloodfinder.extensions.onScrollChanged
import com.efedaniel.bloodfinder.extensions.show
import com.efedaniel.bloodfinder.utils.ApiKeys
import kotlinx.android.synthetic.main.empty_layout.view.*
import javax.inject.Inject

class RequestHistoryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentRequestHistoryBinding
    private lateinit var viewModel: RequestHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestHistoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        (mainActivity.applicationContext as App).component.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RequestHistoryViewModel::class.java)
        binding.viewModel = viewModel

        binding.requestHistoryRecyclerView.apply {
            adapter = RequestHistoryAdapter {
                if (it.status != ApiKeys.PENDING) {
                    findNavController().navigate(RequestHistoryFragmentDirections.actionRequestHistoryFragmentToBloodPostingResponseFragment(it))
                }
            }
            onScrollChanged { mainActivity.invalidateToolbarElevation(it) }
        }
        viewModel.getUserRequestHistory()
        viewModel.emptyViewVisibility.observe(this, Observer {
            if (it) {
                binding.emptyView.show()
                binding.emptyView.emptyTextTitle.text = getString(R.string.empty_request_history)
                binding.emptyView.emptyTextDescription.text = getString(R.string.empty_request_history_description)
            }
        })
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar(getString(R.string.request_history), true)
        invalidateToolbarElevation(0)
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
