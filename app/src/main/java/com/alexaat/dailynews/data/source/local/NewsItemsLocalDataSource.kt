package com.alexaat.dailynews.data.source.local

import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.data.source.DataSource
import javax.inject.Inject

class NewsItemsLocalDataSource @Inject constructor(
    private val dao: NewsItemDatabaseDao
):DataSource{

    override suspend fun getAll():DataState<List<NewsItem>?>{
         return DataState.Success(dao.getAll())
    }

    override suspend fun getLast(): DataState<NewsItem?> {
        return   DataState.Success(dao.getLast())
    }

    override suspend fun insert(newsItems:List<NewsItem>){
        dao.insert(newsItems)
    }

    override suspend fun delete(newsItem: NewsItem){
        dao.delete(newsItem)
    }

}