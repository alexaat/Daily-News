package com.alexaat.dailynews.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.pressBackUnconditionally
import androidx.test.filters.MediumTest
import com.alexaat.dailynews.R
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify


@HiltAndroidTest
@MediumTest
class ArticleFragmentNavigationTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val navController = Mockito.mock(NavController::class.java)

    private val newsItem = NewsItem(
        id = "world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
        webPublicationDate = System.currentTimeMillis()-1000*60*10,
        webTitle = "Coronavirus live news: Ireland surges to worst infection rate in world; Spain sees record rise in weekend cases",
        webUrl = "https://www.theguardian.com/world/live/2021/jan/11/coronavirus-live-news-new-york-opens-mass-vaccination-sites-as-global-cases-pass-90m",
        thumbnail = "https://media.guim.co.uk/ab386fa2003631aceb7c031d683d79e27db496e6/0_175_4000_2400/500.jpg")


    @Test
    fun backButtonPressed_popBackStack(){
        val fragmentArgs = ArticleFragmentArgs(webUrl = newsItem.webUrl, title = newsItem.webTitle)
        launchFragmentInHiltContainer<ArticleFragment>(fragmentArgs.toBundle(), R.style.AppTheme){
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBackUnconditionally()
        verify(navController).popBackStack()
    }

}