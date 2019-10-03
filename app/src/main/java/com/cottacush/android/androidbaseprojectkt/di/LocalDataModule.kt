package com.cottacush.android.androidbaseprojectkt.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.cottacush.android.androidbaseprojectkt.sample.models.BreedDatabase
import com.cottacush.android.androidbaseprojectkt.utils.PrefsUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun providePrefsUtils(prefs: SharedPreferences, gson: Gson): PrefsUtils =
        PrefsUtils(prefs, gson)

    @Provides
    @Singleton
    fun provideGlobalSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences("global_shared_prefs", Context.MODE_PRIVATE)


    @Provides
    @Singleton
    fun provideBreedDatabase(app: Application) : BreedDatabase = Room.databaseBuilder(app,
        BreedDatabase::class.java,
        "breeds-database.db"
    ).build()
}
