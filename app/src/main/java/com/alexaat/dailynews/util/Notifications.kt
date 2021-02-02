package com.alexaat.dailynews.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.alexaat.dailynews.R
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.ui.MainActivity
import com.alexaat.dailynews.util.Constants.NEWS_UPDATE_NOTIFICATION_ID
import com.bumptech.glide.Glide

fun displayNotification(applicationContext:Context,latestArticle: NewsItem){
    val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val contentIntent = Intent(applicationContext, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NEWS_UPDATE_NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val futureTarget = Glide.with(applicationContext)
        .asBitmap()
        .load(latestArticle.thumbnail)
        .submit()
    val bitmap = futureTarget.get()

    val notification = NotificationCompat.Builder(applicationContext,applicationContext.getString(R.string.news_for_you_notification_channel_id))
        .setContentTitle(applicationContext.getString(R.string.latest_news_for_you))
        .setContentText(latestArticle.webTitle)
        .setSmallIcon(R.drawable.ic_dn_notify)
        .setContentIntent(contentPendingIntent)
		.setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setLargeIcon(bitmap)

    Glide.with(applicationContext).clear(futureTarget)

    notificationManager.notify(NEWS_UPDATE_NOTIFICATION_ID,notification.build())

}