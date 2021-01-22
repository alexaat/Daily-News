package com.alexaat.dailynews.data.source.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.util.Event
import com.alexaat.dailynews.util.getCutOffDate

class FakeAndroidTestRepository (
    private val oldNewsItemSetup: NewsItem,
    private val newsItemsSetup: MutableList<NewsItem>,
    private val remoteNewsItemSetup: NewsItem,
    private val networkErrorFlag:Boolean = false) : Repository {

    private val newsItems = mutableListOf<NewsItem>()

    private val _articles = MutableLiveData<List<NewsItem>>()
    override val articles: LiveData<List<NewsItem>>
        get() = _articles

    private val _loadingStatus = MutableLiveData<Event<DataState<Boolean>>>()
    override val loadingStatus: LiveData<Event<DataState<Boolean>>>
        get() = _loadingStatus

    override suspend fun refreshNewsArticles() {

        // Get Local Items
        newsItems.addOrReplace(oldNewsItemSetup)
        for(item in newsItemsSetup){
            newsItems.addOrReplace(item)
        }
        newsItems.removeIf { it.webPublicationDate < getCutOffDate() }
        _articles.postValue(newsItems)

        //Get Remote Items
        _loadingStatus.postValue(Event(DataState.Loading))
        if(networkErrorFlag){
            _loadingStatus.postValue(Event(DataState.Error(Exception("Network error"))))
        }else{
            newsItems.addOrReplace(remoteNewsItemSetup)
            newsItems.removeIf { it.webPublicationDate < getCutOffDate() }
            _loadingStatus.postValue(Event(DataState.Success(true)))
            _articles.postValue(newsItems)
        }
    }

    private fun MutableList<NewsItem>.addOrReplace(newsItem: NewsItem){
        this.removeIf { it.id == newsItem.id}
        this.add(newsItem)
    }
}

