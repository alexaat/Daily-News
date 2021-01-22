package com.alexaat.dailynews.data.source

import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem

interface DataSource{

    suspend fun getAll():DataState<List<NewsItem>?>
    suspend fun getLast():DataState<NewsItem?>
    suspend fun insert(newsItems: List<NewsItem>)
    suspend fun delete(newsItem: NewsItem)

}