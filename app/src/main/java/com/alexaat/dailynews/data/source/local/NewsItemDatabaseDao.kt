package com.alexaat.dailynews.data.source.local

import androidx.room.*
import com.alexaat.dailynews.data.NewsItem

@Dao
interface NewsItemDatabaseDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(newsItems: List<NewsItem>)

    @Query("SELECT * FROM daily_news ORDER BY web_publication_date DESC")
    fun getAll(): List<NewsItem>

    @Query("SELECT * FROM daily_news ORDER BY web_publication_date DESC LIMIT 1")
    fun getLast(): NewsItem

    @Delete
    fun delete(newsItem: NewsItem)
}