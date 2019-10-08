package com.efedaniel.bloodfinder.di

import android.app.Application
import com.efedaniel.bloodfinder.bloodfinder.auth.signin.SignInFragment
import com.efedaniel.bloodfinder.bloodfinder.auth.signup.SignUpFragment
import com.efedaniel.bloodfinder.bloodfinder.home.dashboard.DashboardFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: SignInFragment)
    fun inject(target: SignUpFragment)
    fun inject(target: DashboardFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
