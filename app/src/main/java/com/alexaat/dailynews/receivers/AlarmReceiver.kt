package com.alexaat.dailynews.receivers


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alexaat.dailynews.data.source.remote.ContentApiService
import com.alexaat.dailynews.util.NetworkMapper
import com.alexaat.dailynews.util.displayNotification
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver(){

    @Inject
    lateinit var contentApiService: ContentApiService
    @Inject
    lateinit var mapper: NetworkMapper

    override fun onReceive(context: Context, intent: Intent) {

        val job = Job()
        val coroutineScope = CoroutineScope(job + Dispatchers.IO)
        coroutineScope.launch {
            val lastNewsItemDeferred = contentApiService.getLastAsync()
            try{
                val lastNewsItem = lastNewsItemDeferred.await().response.results
                if(lastNewsItem.isNotEmpty()){
                     displayNotification(context, mapper.mapToNewsItems(lastNewsItem)[0])
                }
            }catch (e:Exception){
         }
        }
    }

}