package com.alexaat.dailynews.di

import com.alexaat.dailynews.data.source.local.NewsItemsLocalDataSource
import com.alexaat.dailynews.data.source.remote.NewsItemsRemoteDataSource
import com.alexaat.dailynews.data.source.repository.DefaultRepository
import com.alexaat.dailynews.data.source.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule{

    @Provides
    @Singleton
    fun provideRepository(newsItemsLocalDataSource: NewsItemsLocalDataSource,
                         newsItemsRemoteDataSource: NewsItemsRemoteDataSource): Repository {
        return DefaultRepository(newsItemsLocalDataSource, newsItemsRemoteDataSource)
    }


}