package com.alexaat.dailynews.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "daily_news")
data class NewsItem(
    @PrimaryKey(autoGenerate = false)
    val id:String,
    @ColumnInfo(name = "web_publication_date")
    val webPublicationDate:Long,
    @ColumnInfo(name = "web_title")
    val webTitle:String,
    @ColumnInfo(name = "web_url")
    val webUrl:String,
    @ColumnInfo(name = "thumbnail")
    val thumbnail:String)