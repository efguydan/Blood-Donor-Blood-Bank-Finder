package com.efedaniel.bloodfinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.efedaniel.bloodfinder.bloodfinder.auth.signin.SignInViewModel
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
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindsSignInViewModel(viewModel: SignInViewModel): ViewModel

    // TODO Add other ViewModels.
}
