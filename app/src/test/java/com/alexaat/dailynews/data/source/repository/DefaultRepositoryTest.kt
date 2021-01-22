package com.alexaat.dailynews.data.source.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.data.source.FakeDataSource
import com.alexaat.dailynews.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultRepositoryTest{

    //Required to fix getOrAwaitValue() run on main thread error
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeDataSourceLocal: FakeDataSource
    private lateinit var fakeDataSourceRemote: FakeDataSource
    private lateinit var defaultRepository: DefaultRepository

    @Before
    fun setup(){
        val localItem1 = NewsItem(
            id = "football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            webPublicationDate = System.currentTimeMillis()-1000*60*60*24*6,
            webTitle = "Chorley v Derby County: FA Cup third round – live!",
            webUrl = "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            thumbnail = "https://media.guim.co.uk/672f5272fc32c30d360b1f6e3107bcdcc1a3af93/0_350_4176_2506/500.jpg"
        )

        val localItem2 = NewsItem(
            id = "football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            webPublicationDate = System.currentTimeMillis()-1000*60*50,
            webTitle = "Chorley v Derby County: FA Cup third round – live!",
            webUrl = "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            thumbnail = "https://media.guim.co.uk/672f5272fc32c30d360b1f6e3107bcdcc1a3af93/0_350_4176_2506/500.jpg"
        )

        val localItem3 = NewsItem(
            id = "world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
            webPublicationDate = System.currentTimeMillis()-1000*60*30,
            webTitle = "Coronavirus live news: Ireland surges to worst infection rate in world; Spain sees record rise in weekend cases",
            webUrl = "https://www.theguardian.com/world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
            thumbnail = "https://media.guim.co.uk/ab386fa2003631aceb7c031d683d79e27db496e6/0_175_4000_2400/500.jpg"
        )

        val remoteItem = NewsItem(
            id = "us-news/live/2021/jan/11/joe-biden-donald-trump-impeachment-capitol-mike-pence-nancy-pelosi-coronavirus-covid-live-updates",
            webPublicationDate = System.currentTimeMillis()-1000*60*10,
            webTitle = "House Democrats move toward impeachment as Biden says 'Trump should not be in office' – live",
            webUrl = "https://www.theguardian.com/us-news/live/2021/jan/11/joe-biden-donald-trump-impeachment-capitol-mike-pence-nancy-pelosi-coronavirus-covid-live-updates",
            thumbnail = "https://media.guim.co.uk/a73d3bf945205ef8b0fc99fc54355b4a7b230b99/0_331_5000_3002/500.jpg"
        )

        fakeDataSourceLocal = FakeDataSource(mutableListOf(localItem1,localItem2,localItem3))
        fakeDataSourceRemote = FakeDataSource(mutableListOf(remoteItem))
        defaultRepository = DefaultRepository(newsItemsLocalDataSource = fakeDataSourceLocal, newsItemsRemoteDataSource = fakeDataSourceRemote, Dispatchers.Unconfined)
    }


    @Test
    fun `refresh articles and check articles live data`() = runBlockingTest{
            defaultRepository.refreshNewsArticles()
            val newNumberOfArticles = defaultRepository.articles.getOrAwaitValueTest().size
            assertThat(newNumberOfArticles).isEqualTo(3)
    }


    @Test
    fun `refresh articles and check loadingStatus live data`() = runBlockingTest{
         defaultRepository.refreshNewsArticles()
         val event = defaultRepository.loadingStatus.getOrAwaitValueTest().getContentIfNotHandled()
         assertThat(event is DataState.Success).isTrue()
    }

}