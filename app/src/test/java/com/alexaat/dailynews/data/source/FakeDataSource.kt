package com.alexaat.dailynews.data.source

import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem

class FakeDataSource(var newsItems: MutableList<NewsItem>? = mutableListOf()) :DataSource{


    override suspend fun getAll(): DataState<List<NewsItem>?> {
        newsItems?.let{
            return DataState.Success(it)
        }
        return DataState.Error(Exception("articles not found"))
    }

    override suspend fun getLast(): DataState<NewsItem?> {
        newsItems?.let{
            return DataState.Success(it.last())
        }
        return DataState.Error(Exception("article not found"))
    }

    override suspend fun insert(newsItems: List<NewsItem>) {
        this.newsItems?.addAll(newsItems)
    }

    override suspend fun delete(newsItem: NewsItem) {
        newsItems?.remove(newsItem)
    }

}