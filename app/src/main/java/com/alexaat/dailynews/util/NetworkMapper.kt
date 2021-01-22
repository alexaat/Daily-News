package com.alexaat.dailynews.util

import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.data.source.remote.Result
import javax.inject.Inject

class NetworkMapper @Inject constructor(){
    private fun mapToNewsItem(result: Result): NewsItem {

        return NewsItem(
            id=result.id,
            webPublicationDate = dateToMilli(result.webPublicationDate),
            webTitle = result.webTitle,
            webUrl = result.webUrl,
            thumbnail = result.fields!!.thumbnail
        )
    }

    fun mapToNewsItems(results: List<Result>):List<NewsItem>{
        val newsItems = ArrayList<NewsItem>()
        for(result in results){
            if(result.fields!=null){
                newsItems.add(mapToNewsItem(result))
            }
        }
        return newsItems
    }
}
