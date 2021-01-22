package com.alexaat.dailynews.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexaat.dailynews.R
import com.alexaat.dailynews.util.cancelAllAlarms
import com.alexaat.dailynews.util.setAlarms
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(
            channelId=  getString(R.string.news_for_you_notification_channel_id),
            channelName = getString(R.string.news_for_you_notification_channel_name)
        )

    }

    private fun createNotificationChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText =  getString(R.string.news_for_you_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = descriptionText
                setShowBadge(false)
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
            }
            val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    override fun onResume() {
        cancelAllAlarms(applicationContext)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        setAlarms(applicationContext)
    }

}