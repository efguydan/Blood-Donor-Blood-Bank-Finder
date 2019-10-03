package com.cottacush.android.androidbaseprojectkt.di

import android.app.Application
import com.cottacush.android.androidbaseprojectkt.sample.catlist.BreedListFragment
import com.cottacush.android.androidbaseprojectkt.workmanager.RefreshDataWork
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: BreedListFragment)
    fun inject(target: RefreshDataWork)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
