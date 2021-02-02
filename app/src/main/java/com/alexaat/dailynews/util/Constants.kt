package com.alexaat.dailynews.util

object Constants{
    const val BASE_URL = "https://content.guardianapis.com/"
    const val DATABASE_NAME = "daily_news_database"
    const val dateToMilliPatten = "yyyy-MM-dd'T'HH:mm:ss"
    const val milliToDatePatten = "dd-MM-yyyy HH:mm:ss"
    const val WORK_NAME = "com.alexaat.dailynews.updatenewsarticleswork"

    // Alarms
    const val SECOND = 1000L
    const val MINUTE = 60* SECOND
    const val HOUR = 60* MINUTE
    const val DAY = 24*HOUR

    const val FIRST_ALARM = DAY
    const val SECOND_ALARM = 7* DAY
    const val THIRD_ALARM = 28* DAY

    const val REQUEST_CODE_FIRST_ALARM = 10
    const val REQUEST_CODE_SECOND_ALARM = 11
    const val REQUEST_CODE_THIRD_ALARM = 12

    //Notifications
    const val NEWS_UPDATE_NOTIFICATION_ID = 1
}