package com.alexaat.dailynews.work

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.*
import com.alexaat.dailynews.data.source.repository.DefaultRepository
import retrofit2.HttpException

class MyWorker  @WorkerInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: DefaultRepository
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try{
            repository.refreshNewsArticles()
            Result.success()
        }catch(e: HttpException){
            Result.retry()
        }
    }
}
