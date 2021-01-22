package com.alexaat.dailynews.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.alexaat.dailynews.data.DataState
import com.alexaat.dailynews.data.NewsItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class NewsItemDatabaseDaoAndroidTest{

    //Required to fix getOrAwaitValue() run on main thread error
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NewsItemDatabase
    private lateinit var dao: NewsItemDatabaseDao

    private lateinit var newsItem1: NewsItem
    private lateinit var newsItem2: NewsItem
    private lateinit var newsItem3: NewsItem

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NewsItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.newsItemDatabaseDao

        newsItem1 = NewsItem(
            id = "football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            webPublicationDate = 1609850611000,
            webTitle = "Chorley v Derby County: FA Cup third round – live!",
            webUrl = "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
            thumbnail = "https://media.guim.co.uk/672f5272fc32c30d360b1f6e3107bcdcc1a3af93/0_350_4176_2506/500.jpg"
        )

        newsItem2 = NewsItem(
            id = "world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
            webPublicationDate = 1610397401000,
            webTitle = "Coronavirus live news: Ireland surges to worst infection rate in world; Spain sees record rise in weekend cases",
            webUrl = "https://www.theguardian.com/world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
            thumbnail = "https://media.guim.co.uk/ab386fa2003631aceb7c031d683d79e27db496e6/0_175_4000_2400/500.jpg"
        )

        newsItem3 = NewsItem(
            id = "us-news/live/2021/jan/11/joe-biden-donald-trump-impeachment-capitol-mike-pence-nancy-pelosi-coronavirus-covid-live-updates",
            webPublicationDate = 1610398670000,
            webTitle = "House Democrats move toward impeachment as Biden says 'Trump should not be in office' – live",
            webUrl = "https://www.theguardian.com/us-news/live/2021/jan/11/joe-biden-donald-trump-impeachment-capitol-mike-pence-nancy-pelosi-coronavirus-covid-live-updates",
            thumbnail = "https://media.guim.co.uk/a73d3bf945205ef8b0fc99fc54355b4a7b230b99/0_331_5000_3002/500.jpg"
        )


    }


    @Test
    fun insert_getAllTest()= runBlockingTest {

        dao.insert(arrayListOf(newsItem1,newsItem2,newsItem3))
        val newsItems = dao.getAll()

        assertThat(newsItems).contains(newsItem1)
        assertThat(newsItems).contains(newsItem2)
        assertThat(newsItems).contains(newsItem3)
    }


    @Test
    fun getLastTest(){
        dao.insert(arrayListOf(newsItem1,newsItem2,newsItem3))
        val newsItem = dao.getLast()
        assertThat(newsItem.id).isEqualTo(newsItem3.id)
    }

    @Test
    fun deleteTest(){
        dao.insert(arrayListOf(newsItem1,newsItem2,newsItem3))
        dao.delete(newsItem2)
        val newsItems = dao.getAll()
        assertThat(newsItems).doesNotContain(newsItem2)
    }

    @After
    fun teardown(){
        database.close()
    }


}