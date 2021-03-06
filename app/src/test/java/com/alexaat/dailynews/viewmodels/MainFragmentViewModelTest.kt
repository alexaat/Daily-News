package com.alexaat.dailynews.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexaat.dailynews.MainCoroutineRule
import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.getOrAwaitValueTest
import com.alexaat.dailynews.repository.FakeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainFragmentViewModelTest {

    //Required to fix getOrAwaitValue() run on main thread error
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Fix Module with the Main dispatcher had failed to initialize error
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainFragmentViewModel:MainFragmentViewModel

    private lateinit var newsItemOld: NewsItem
    private lateinit var newsItems: MutableList<NewsItem>
    private lateinit var remoteNewsItem: NewsItem

    @Before
    fun setUp() {
            newsItemOld = NewsItem(
            id = "sport/2021/jan/18/england-skills-coach-jason-ryles-six-nations-covid-19",
            webPublicationDate = System.currentTimeMillis()-1000*60*60*24*6,
            webTitle = "England skills coach Jason Ryles to miss Six Nations due to Covid-19 lockdown",
            webUrl = "https://www.theguardian.com/sport/2021/jan/18/england-skills-coach-jason-ryles-six-nations-covid-19",
            thumbnail = "https://media.guim.co.uk/88341a68879a11a6fa6adb789f7c1425af417921/33_110_3545_2127/500.jpg")

            val newsItem1 = NewsItem(
            id = "football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            webPublicationDate = System.currentTimeMillis()-1000*60*15,
            webTitle = "Chorley v Derby County: FA Cup third round – live!",
            webUrl = "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            thumbnail = "https://media.guim.co.uk/672f5272fc32c30d360b1f6e3107bcdcc1a3af93/0_350_4176_2506/500.jpg")

            val newsItem2 = NewsItem(
            id = "world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
            webPublicationDate = System.currentTimeMillis()-1000*60*10,
            webTitle = "Coronavirus live news: Ireland surges to worst infection rate in world; Spain sees record rise in weekend cases",
            webUrl = "https://www.theguardian.com/world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
            thumbnail = "https://media.guim.co.uk/ab386fa2003631aceb7c031d683d79e27db496e6/0_175_4000_2400/500.jpg")

            newsItems = mutableListOf(newsItem1,newsItem2)

            remoteNewsItem = NewsItem(
            id = "world/live/2021/jan/18/coronavirus-live-news-brazil-approves-two-covid-vaccines-for-emergency-use-as-us-nears-400000-deaths",
            webPublicationDate = System.currentTimeMillis()-1000*5,
            webTitle = "Coronavirus live news: UK had world's highest Covid death toll last week; more countries record new variant",
            webUrl = "https://www.theguardian.com/world/live/2021/jan/18/coronavirus-live-news-brazil-approves-two-covid-vaccines-for-emergency-use-as-us-nears-400000-deaths",
            thumbnail = "https://media.guim.co.uk/5c51059b0f500c57920d7412b864228601bb913a/0_121_3936_2362/500.jpg")

        mainFragmentViewModel = MainFragmentViewModel(repository = FakeRepository(
            oldNewsItemSetup = newsItemOld,
            newsItemsSetup = newsItems,
            remoteNewsItemSetup = remoteNewsItem),
            dispatchers = Dispatchers.Main,savedStateHandle = null)
    }

    @Test
    fun `refreshArticles and check that new articles are added`(){
        val currentNumberOfArticles = mainFragmentViewModel.articles.getOrAwaitValueTest().size
        assertThat(currentNumberOfArticles).isEqualTo(3)

    }

    @Test
    fun `on item clicked is fired`(){
        val newsItem = NewsItem(
            id = "football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            webPublicationDate = System.currentTimeMillis()-1000*60*50,
            webTitle = "Chorley v Derby County: FA Cup third round – live!",
            webUrl = "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            thumbnail = "https://media.guim.co.uk/672f5272fc32c30d360b1f6e3107bcdcc1a3af93/0_350_4176_2506/500.jpg"
        )
        mainFragmentViewModel.onItemClicked(newsItem)
        val event = mainFragmentViewModel.navigateToArticleFragment.getOrAwaitValueTest()
        assertThat(event.getContentIfNotHandled()).isNotNull()
    }

    @Test
    fun `check loadingStatus success`(){
        mainFragmentViewModel.refreshArticles()
        val loadingStatus = mainFragmentViewModel.loadingStatus.getOrAwaitValueTest().getContentIfNotHandled()
        assertThat(loadingStatus is DataState.Success).isTrue()

    }

    @Test
    fun `check loadingStatus error`(){
        mainFragmentViewModel = MainFragmentViewModel(repository = FakeRepository(
                oldNewsItemSetup = newsItemOld,
                newsItemsSetup = newsItems,
                remoteNewsItemSetup = remoteNewsItem,
                networkErrorFlag = true),
            dispatchers = Dispatchers.Main,savedStateHandle = null)
        mainFragmentViewModel.refreshArticles()
        val loadingStatus = mainFragmentViewModel.loadingStatus.getOrAwaitValueTest().getContentIfNotHandled()
        assertThat(loadingStatus is DataState.Error).isTrue()
    }
}