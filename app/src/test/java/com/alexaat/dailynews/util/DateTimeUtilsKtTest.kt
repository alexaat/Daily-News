package com.alexaat.dailynews.util



import com.google.common.truth.Truth.assertThat
import org.junit.Test



class DateTimeUtilsKtTest {

    @Test
    fun dateToMilliTest() {
        val date = "2020-02-15T15:48:10Z"
        val milli = dateToMilli(date)
        val expected = 1581781690000
        assertThat(expected).isEqualTo(milli)

    }

    @Test
    fun milliToDateTest() {
        val result = "18-12-2020 11:15:45"
        val expected = 1608290145000
        val date = milliToDate(expected)
        assertThat(result).isEqualTo(date)

    }

    @Test
    fun cutOffDateTest() {

        //Delete article if it is older than 3 days

        val cutOffDate = getCutOffDate()

        val currentTime = System.currentTimeMillis()
        val dateToKeep = currentTime - 1000*60*60*24*3+1000*60
        val dateToDelete =  currentTime - 1000*60*60*24*3-1000*60

        assertThat(cutOffDate).isGreaterThan(dateToDelete)
        assertThat(cutOffDate).isLessThan(dateToKeep)

    }
}