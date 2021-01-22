package com.alexaat.dailynews.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alexaat.dailynews.R
import com.alexaat.dailynews.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class ArticleFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

@Test
fun articleFragment_DisplayedInUi(){
    val bundle = ArticleFragmentArgs(
        webUrl = "https://www.theguardian.com/football/live/2021/jan/09/chorley-v-derby-county-fa-cup-third-round-live",
        title = "Chorley v Derby County: FA Cup third round – live!").toBundle()
    launchFragmentInHiltContainer<ArticleFragment>(bundle, R.style.AppTheme)
    onView(withId(R.id.web_view)).check(matches(isDisplayed()))
    onView(ViewMatchers.withText("Chorley v Derby County: FA Cup third round – live!")).check(matches(
        isDisplayed()))
}


}