package com.alexaat.dailynews.eventlisteners


import com.alexaat.dailynews.data.NewsItem

class OnItemClickedListener (private val listener: (NewsItem)->Unit){
    fun onClick(newsItem: NewsItem){
         listener(newsItem)
    }
}