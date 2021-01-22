package com.alexaat.dailynews.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.data.source.repository.Repository
import com.alexaat.dailynews.util.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragmentViewModel @ViewModelInject
constructor (private val repository: Repository,
             private val dispatchers: CoroutineDispatcher,
             @Assisted private val savedStateHandle: SavedStateHandle?): ViewModel(){

   private val _navigateToArticleFragment = MutableLiveData<Event<NewsItem>>()
   val navigateToArticleFragment:LiveData<Event<NewsItem>>
      get() = _navigateToArticleFragment

   init{
      refreshArticles()
   }

   val articles = repository.articles

   val loadingStatus = repository.loadingStatus

   fun refreshArticles(){
      viewModelScope.launch {
         withContext(dispatchers){
            repository.refreshNewsArticles()
         }
      }
   }

   fun onItemClicked(newsItem: NewsItem){
      _navigateToArticleFragment.value = Event(newsItem)
   }

}