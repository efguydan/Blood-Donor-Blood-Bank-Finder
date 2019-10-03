package com.cottacush.android.androidbaseprojectkt.sample.models

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseBreedModel::class], version = 1, exportSchema = false)
@TypeConverters(WeightConverter::class)

abstract class BreedDatabase : RoomDatabase() {
    abstract val breedDao: BreedDao
}
