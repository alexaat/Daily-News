package com.alexaat.dailynews.di

import android.content.Context
import androidx.room.Room
import com.alexaat.dailynews.data.source.local.NewsItemDatabase
import com.alexaat.dailynews.data.source.local.NewsItemDatabaseDao
import com.alexaat.dailynews.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule{

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NewsItemDatabase {
        return Room.databaseBuilder(context, NewsItemDatabase::class.java,DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideDao(newsItemDatabase: NewsItemDatabase): NewsItemDatabaseDao {
        return newsItemDatabase.newsItemDatabaseDao
    }

}