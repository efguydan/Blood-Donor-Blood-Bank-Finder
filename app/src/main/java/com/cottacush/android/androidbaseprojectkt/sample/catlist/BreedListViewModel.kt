package com.cottacush.android.androidbaseprojectkt.sample.catlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cottacush.android.androidbaseprojectkt.Utils
import com.cottacush.android.androidbaseprojectkt.base.BaseViewModel
import com.cottacush.android.androidbaseprojectkt.sample.ExampleRepository
import com.cottacush.android.androidbaseprojectkt.sample.models.Breed
import kotlinx.coroutines.launch
import javax.inject.Inject

class BreedListViewModel @Inject constructor(private val exampleRepository: ExampleRepository) :
    BaseViewModel() {
    val breeds = exampleRepository.breeds

    private val _navigateToSelectedBreed = MutableLiveData<Breed>()

    val navigateToSelectedBreed: LiveData<Breed>
        get() = _navigateToSelectedBreed


    init {
        viewModelScope.launch {
            //TODO don't refresh every time. schedule refresh with workManager...
            // only when there is network. The refresh strategy could also be more complex.
            exampleRepository.refreshBreeds(Utils.LIMIT)
        }
    }


    fun displayCatBreedDetails(breed: Breed) {
        _navigateToSelectedBreed.value = breed
    }

    fun displayCatBreedDetailsComplete() {
        _navigateToSelectedBreed.value = null
    }
}
