package com.efedaniel.bloodfinder.di

import android.app.Application
import com.efedaniel.bloodfinder.bloodfinder.auth.forgotpassword.ForgotPasswordFragment
import com.efedaniel.bloodfinder.bloodfinder.auth.signin.SignInFragment
import com.efedaniel.bloodfinder.bloodfinder.auth.signup.SignUpFragment
import com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty.BloodAvailabilityFragment
import com.efedaniel.bloodfinder.bloodfinder.home.bloodpostingdetails.BloodPostingDetailsFragment
import com.efedaniel.bloodfinder.bloodfinder.home.bloodrequest.BloodRequestFragment
import com.efedaniel.bloodfinder.bloodfinder.home.bloodrequestresults.BloodResultsFragment
import com.efedaniel.bloodfinder.bloodfinder.home.dashboard.DashboardFragment
import com.efedaniel.bloodfinder.bloodfinder.home.donationhistory.DonationHistoryFragment
import com.efedaniel.bloodfinder.bloodfinder.home.profile.ProfileFragment
import com.efedaniel.bloodfinder.bloodfinder.home.requesthistory.RequestHistoryFragment
import com.efedaniel.bloodfinder.bloodfinder.home.viewprofile.ViewProfileFragment
import com.efedaniel.bloodfinder.bloodfinder.maps.selectlocation.SelectLocationFragment
import com.efedaniel.bloodfinder.bloodfinder.notifications.NotificationHandlerService
import com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingRequest.BloodPostingRequestFragment
import com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingResponse.BloodPostingResponseFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: NotificationHandlerService)

    fun inject(target: SignInFragment)
    fun inject(target: SignUpFragment)
    fun inject(target: ForgotPasswordFragment)
    fun inject(target: ProfileFragment)
    fun inject(target: DashboardFragment)
    fun inject(target: BloodAvailabilityFragment)
    fun inject(target: BloodRequestFragment)
    fun inject(target: BloodResultsFragment)
    fun inject(target: SelectLocationFragment)
    fun inject(target: BloodPostingDetailsFragment)
    fun inject(target: BloodPostingRequestFragment)
    fun inject(target: BloodPostingResponseFragment)
    fun inject(target: ViewProfileFragment)
    fun inject(target: RequestHistoryFragment)
    fun inject(target: DonationHistoryFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
