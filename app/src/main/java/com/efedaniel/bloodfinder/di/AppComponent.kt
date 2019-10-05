package com.efedaniel.bloodfinder.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    //TODO Inject To Fragments
//    fun inject(target: BreedListFragment)
//    fun inject(target: RefreshDataWork)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
