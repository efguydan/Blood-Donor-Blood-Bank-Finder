package com.cottacush.android.androidbaseprojectkt.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cottacush.android.androidbaseprojectkt.App
import com.cottacush.android.androidbaseprojectkt.sample.ExampleRepository
import retrofit2.HttpException
import javax.inject.Inject

class RefreshDataWork(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    @Inject
    lateinit var repository: ExampleRepository

    companion object {
        const val WORK_NAME = "RefreshDataWork"
    }

    init {
        (appContext as App).component.inject(this)
    }


    override suspend fun doWork(): Payload {

        return try {
             repository.refreshBreeds(40)
            Payload(Result.SUCCESS)
        } catch (exception: HttpException) {
            Payload(Result.RETRY)
        }
    }

}
