package com.efedaniel.bloodfinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.efedaniel.bloodfinder.bloodfinder.auth.forgotpassword.ForgotPasswordViewModel
import com.efedaniel.bloodfinder.bloodfinder.auth.signin.SignInViewModel
import com.efedaniel.bloodfinder.bloodfinder.auth.signup.SignUpViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty.BloodAvailabilityViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.bloodrequest.BloodRequestViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults.BloodResultsViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.dashboard.DashboardViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.profile.ProfileViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindsSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel::class)
    abstract fun bindsForgotPasswordViewModel(viewModel: ForgotPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun bindsDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindsProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BloodAvailabilityViewModel::class)
    abstract fun bindsBloodAvailabilityViewModel(viewModel: BloodAvailabilityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BloodRequestViewModel::class)
    abstract fun bindsBloodRequestViewModel(viewModel: BloodRequestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BloodResultsViewModel::class)
    abstract fun bindsBloodResultsViewModel(viewModel: BloodResultsViewModel): ViewModel

    // TODO Add other ViewModels.
}
