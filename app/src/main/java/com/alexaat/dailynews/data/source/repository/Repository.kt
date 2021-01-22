package com.alexaat.dailynews.data.source.repository

import androidx.lifecycle.LiveData
import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.util.Event

interface Repository{

    val articles: LiveData<List<NewsItem>>  

    val loadingStatus: LiveData<Event<DataState<Boolean>>>

    suspend fun refreshNewsArticles()

}