package com.efedaniel.bloodfinder.di

import android.app.Application
import com.efedaniel.bloodfinder.bloodfinder.auth.forgotpassword.ForgotPasswordFragment
import com.efedaniel.bloodfinder.bloodfinder.auth.signin.SignInFragment
import com.efedaniel.bloodfinder.bloodfinder.auth.signup.SignUpFragment
import com.efedaniel.bloodfinder.bloodfinder.home.bloodavailabilty.BloodAvailabilityFragment
import com.efedaniel.bloodfinder.bloodfinder.home.dashboard.DashboardFragment
import com.efedaniel.bloodfinder.bloodfinder.home.profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: SignInFragment)
    fun inject(target: SignUpFragment)
    fun inject(target: ForgotPasswordFragment)
    fun inject(target: ProfileFragment)
    fun inject(target: DashboardFragment)
    fun inject(target: BloodAvailabilityFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
