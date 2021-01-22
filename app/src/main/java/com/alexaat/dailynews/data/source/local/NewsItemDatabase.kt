package com.alexaat.dailynews.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexaat.dailynews.data.NewsItem


@Database(entities = [NewsItem::class], version = 1)
abstract class NewsItemDatabase: RoomDatabase() {

    abstract val newsItemDatabaseDao: NewsItemDatabaseDao

}