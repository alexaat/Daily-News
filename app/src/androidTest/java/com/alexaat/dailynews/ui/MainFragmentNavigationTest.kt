package com.alexaat.dailynews.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.alexaat.dailynews.R
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.data.source.repository.FakeAndroidTestRepository
import com.alexaat.dailynews.data.source.repository.Repository
import com.alexaat.dailynews.di.RepositoryModule
import com.alexaat.dailynews.launchFragmentInHiltContainer
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
@MediumTest
class MainFragmentNavigationTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val newsItemOld = NewsItem(
        id = "sport/2021/jan/18/england-skills-coach-jason-ryles-six-nations-covid-19",
        webPublicationDate = System.currentTimeMillis()-1000*60*60*24*6,
        webTitle = "England skills coach Jason Ryles to miss Six Nations due to Covid-19 lockdown",
        webUrl = "https://www.theguardian.com/sport/2021/jan/18/england-skills-coach-jason-ryles-six-nations-covid-19",
        thumbnail = "https://media.guim.co.uk/88341a68879a11a6fa6adb789f7c1425af417921/33_110_3545_2127/500.jpg")

    private val newsItem1 = NewsItem(
        id = "football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
        webPublicationDate = System.currentTimeMillis()-1000*60*15,
        webTitle = "Chorley v Derby County: FA Cup third round – live!",
        webUrl = "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
        thumbnail = "https://media.guim.co.uk/672f5272fc32c30d360b1f6e3107bcdcc1a3af93/0_350_4176_2506/500.jpg")

    private val newsItem2 = NewsItem(
        id = "world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
        webPublicationDate = System.currentTimeMillis()-1000*60*10,
        webTitle = "Coronavirus live news: Ireland surges to worst infection rate in world; Spain sees record rise in weekend cases",
        webUrl = "https://www.theguardian.com/world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
        thumbnail = "https://media.guim.co.uk/ab386fa2003631aceb7c031d683d79e27db496e6/0_175_4000_2400/500.jpg")

    private val newsItems = mutableListOf(newsItem1,newsItem2)

    private val remoteNewsItem = NewsItem(
        id = "world/live/2021/jan/18/coronavirus-live-news-brazil-approves-two-covid-vaccines-for-emergency-use-as-us-nears-400000-deaths",
        webPublicationDate = System.currentTimeMillis()-1000*5,
        webTitle = "Coronavirus live news: UK had world's highest Covid death toll last week; more countries record new variant",
        webUrl = "https://www.theguardian.com/world/live/2021/jan/18/coronavirus-live-news-brazil-approves-two-covid-vaccines-for-emergency-use-as-us-nears-400000-deaths",
        thumbnail = "https://media.guim.co.uk/5c51059b0f500c57920d7412b864228601bb913a/0_121_3936_2362/500.jpg")

    @BindValue
    @JvmField
    val repository: Repository = FakeAndroidTestRepository(
        oldNewsItemSetup = newsItemOld,
        newsItemsSetup = newsItems,
        remoteNewsItemSetup = remoteNewsItem)

    @ExperimentalCoroutinesApi
    @Test
    fun clickNewsItem_NavigateToArticleFragment() = runBlockingTest{
        repository.refreshNewsArticles()

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MainFragment>(null, R.style.AppTheme){
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withText("Chorley v Derby County: FA Cup third round – live!")).perform(click())

        verify(navController).navigate(
            MainFragmentDirections.actionMainFragmentToArticleFragment(
                "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
                "Chorley v Derby County: FA Cup third round – live!"))

    }

}