package com.alexaat.dailynews.data.source.remote

import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.data.source.DataSource
import com.alexaat.dailynews.util.NetworkMapper
import javax.inject.Inject

class NewsItemsRemoteDataSource @Inject constructor(
    private val contentApiService: ContentApiService,
    private val mapper: NetworkMapper
):DataSource{

    override suspend fun getAll(): DataState<List<NewsItem>?> {
        val getServerRequestResultDeferred = contentApiService.getServerRequestResultAsync()
        try {
            val serverRequestResult = getServerRequestResultDeferred.await()
            return DataState.Success(mapper.mapToNewsItems(serverRequestResult.response.results))
            }catch (e: Exception){
                return DataState.Error(e)
            }
    }

    override suspend fun getLast(): DataState<NewsItem?> {
        val getLastDeferred = contentApiService.getLastAsync()
        try {
            val lastDeferred = getLastDeferred.await()
            val data = mapper.mapToNewsItems(lastDeferred.response.results)
            if(data.isNullOrEmpty()) return DataState.Error(Exception("No data"))
            return DataState.Success(mapper.mapToNewsItems(lastDeferred.response.results)[0])
        }catch (e: Exception){
            return DataState.Error(e)
        }
    }

    override suspend fun insert(newsItems: List<NewsItem>) {
        // Not in use
    }

    override suspend fun delete(newsItem: NewsItem) {
       // Not in use
    }
}