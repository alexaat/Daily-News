package com.alexaat.dailynews.data.source.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexaat.dailynews.R
import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.data.source.DataSource
import com.alexaat.dailynews.di.DataSourceModule
import com.alexaat.dailynews.util.Event
import com.alexaat.dailynews.util.getCutOffDate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    @DataSourceModule.LocalStorageQualifier private val newsItemsLocalDataSource: DataSource,
    @DataSourceModule.RemoteStorageQualifier private val newsItemsRemoteDataSource: DataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): Repository {

    @ApplicationContext
    lateinit var context: Context

    private val _articles = MutableLiveData<List<NewsItem>>(listOf())
    override val articles: LiveData<List<NewsItem>>
        get() = _articles

    private val _loadingStatus = MutableLiveData<Event<DataState<Boolean>>>()
    override val loadingStatus: LiveData<Event<DataState<Boolean>>>
        get() = _loadingStatus


    override suspend fun refreshNewsArticles() {
            readFromDatabase()
            readFromNetwork()
            readFromDatabase()
    }

    private suspend fun readFromDatabase() {
        //1. Get articles from database
        val newsItemsLocalDataState = newsItemsLocalDataSource.getAll()
        //2. Check articles from database, delete old and update articles live data
        when (newsItemsLocalDataState) {
            is DataState.Error -> {
                _loadingStatus.postValue(Event(DataState.Error(Exception(context.getString(R.string.error_while_reading_from_database)))))
                return
            }
            is DataState.Success -> {
                val newsItemsLocal = newsItemsLocalDataState.data
                if (!newsItemsLocal.isNullOrEmpty()) {
                    val listNewsItemsLocal = newsItemsLocal.toList()
                    val modifiedNewsItemsLocal = mutableListOf<NewsItem>()
                    for(newsItem in listNewsItemsLocal){
                        if(newsItem.webPublicationDate<getCutOffDate()){
                           newsItemsLocalDataSource.delete(newsItem)
                        }else{
                            modifiedNewsItemsLocal.add(newsItem)
                        }
                    }
                    _articles.postValue(modifiedNewsItemsLocal)
                }
            }
        }
    }

    private suspend fun readFromNetwork() {
        //1. Get articles from network
        _loadingStatus.postValue(Event(DataState.Loading))
        val newsItemsRemoteDataState = newsItemsRemoteDataSource.getAll()
        //2. Check network response, inset into database, delete old
        when (newsItemsRemoteDataState) {
            is DataState.Error -> {
                _loadingStatus.postValue(Event(DataState.Error(newsItemsRemoteDataState.exception)))
                return
            }
            is DataState.Success -> {
                _loadingStatus.postValue(Event(DataState.Success(true)))
                val newsItemsRemote = newsItemsRemoteDataState.data
                if (!newsItemsRemote.isNullOrEmpty()) {
                    newsItemsLocalDataSource.insert(newsItemsRemote)
                }
            }
        }
    }
}

