package com.efedaniel.bloodfinder.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.efedaniel.bloodfinder.bloodfinder.auth.forgotpassword.ForgotPasswordViewModel
import com.efedaniel.bloodfinder.bloodfinder.auth.signin.SignInViewModel
import com.efedaniel.bloodfinder.bloodfinder.auth.signup.SignUpViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty.BloodAvailabilityViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.bloodpostingdetails.BloodPostingDetailsViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.bloodrequest.BloodRequestViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults.BloodResultsViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.dashboard.DashboardViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.donationhistory.DonationHistoryViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.profile.ProfileViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.requesthistory.RequestHistoryViewModel
import com.efedaniel.bloodfinder.bloodfinder.home.viewprofile.ViewProfileViewModel
import com.efedaniel.bloodfinder.bloodfinder.maps.selectlocation.SelectLocationViewModel
import com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingRequest.BloodPostingRequestViewModel
import com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingResponse.BloodPostingResponseViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(SelectLocationViewModel::class)
    abstract fun bindsSelectLocationViewModel(viewModel: SelectLocationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BloodPostingDetailsViewModel::class)
    abstract fun bindsBloodPostingDetailsViewModel(viewModel: BloodPostingDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BloodPostingRequestViewModel::class)
    abstract fun bindsBloodPostingRequestViewModel(viewModel: BloodPostingRequestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BloodPostingResponseViewModel::class)
    abstract fun bindsBloodPostingResponseViewModel(viewModel: BloodPostingResponseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ViewProfileViewModel::class)
    abstract fun bindsViewProfileViewModel(viewModel: ViewProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RequestHistoryViewModel::class)
    abstract fun bindsRequestHistoryViewModel(viewModel: RequestHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DonationHistoryViewModel::class)
    abstract fun bindsDonationHistoryViewModel(viewModel: DonationHistoryViewModel): ViewModel
}
