package com.cottacush.android.androidbaseprojectkt.sample.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BreedDao {

    @Query("SELECT * FROM databasebreedmodel")
    fun getAllBreed(): LiveData<List<DatabaseBreedModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBreed(vararg breeds: DatabaseBreedModel)

    @Delete
    fun delete(user: DatabaseBreedModel)

    @Query("DELETE FROM databasebreedmodel")
    fun dropTable()
}



