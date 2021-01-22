package com.alexaat.dailynews.di

import com.alexaat.dailynews.data.source.DataSource
import com.alexaat.dailynews.data.source.local.NewsItemDatabaseDao
import com.alexaat.dailynews.data.source.local.NewsItemsLocalDataSource
import com.alexaat.dailynews.data.source.remote.ContentApiService
import com.alexaat.dailynews.data.source.remote.NewsItemsRemoteDataSource
import com.alexaat.dailynews.util.NetworkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LocalStorageQualifier

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteStorageQualifier

    @Singleton
    @Provides
    @LocalStorageQualifier
    fun provideNewsItemsLocalDataSource(dao: NewsItemDatabaseDao): DataSource {
        return NewsItemsLocalDataSource(dao)
    }

    @Singleton
    @Provides
    @RemoteStorageQualifier
    fun provideNewsItemsRemoteDataSource(
        contentApiService: ContentApiService,
        mapper: NetworkMapper
    ): DataSource {
        return NewsItemsRemoteDataSource(contentApiService, mapper)
    }
}