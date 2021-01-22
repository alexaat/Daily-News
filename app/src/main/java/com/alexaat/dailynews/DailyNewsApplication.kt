package com.alexaat.dailynews

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.alexaat.dailynews.util.Constants.WORK_NAME
import com.alexaat.dailynews.work.MyWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class DailyNewsApplication: Application(), Configuration.Provider{

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        val constraints = getConstraints()
        enqueueWorker(constraints)
    }

    private fun enqueueWorker(constraints: Constraints) {

        val repeatingRequest = PeriodicWorkRequestBuilder<MyWorker>(1,TimeUnit.DAYS)
            .setConstraints(constraints)
            .setInitialDelay(60, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.REPLACE,repeatingRequest)
    }

    private fun getConstraints():Constraints{
        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    setRequiresDeviceIdle(true)}
            }
            .build()
    }
}