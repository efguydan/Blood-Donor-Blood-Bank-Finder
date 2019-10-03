package com.cottacush.android.androidbaseprojectkt.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cottacush.android.androidbaseprojectkt.sample.catlist.BreedListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ExampleAppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BreedListViewModel::class)
    abstract fun bindContactSourcesViewModel(viewModel: BreedListViewModel): ViewModel

    // TODO Add other ViewModels.
}
