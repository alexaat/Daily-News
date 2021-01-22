package com.alexaat.dailynews.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import com.alexaat.dailynews.receivers.AlarmReceiver
import com.alexaat.dailynews.util.Constants.FIRST_ALARM
import com.alexaat.dailynews.util.Constants.REQUEST_CODE_FIRST_ALARM
import com.alexaat.dailynews.util.Constants.REQUEST_CODE_SECOND_ALARM
import com.alexaat.dailynews.util.Constants.REQUEST_CODE_THIRD_ALARM
import com.alexaat.dailynews.util.Constants.SECOND_ALARM
import com.alexaat.dailynews.util.Constants.THIRD_ALARM


private lateinit var notifyPendingIntent1: PendingIntent
private lateinit var notifyPendingIntent2: PendingIntent
private lateinit var notifyPendingIntent3: PendingIntent

fun setAlarms(app: Context){
    setPendingIntents(app)
    val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    var triggerTime = System.currentTimeMillis()+FIRST_ALARM
    AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager,
    AlarmManager.RTC_WAKEUP,triggerTime,notifyPendingIntent1)

    triggerTime = System.currentTimeMillis()+SECOND_ALARM
    AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager,
    AlarmManager.RTC_WAKEUP,triggerTime,notifyPendingIntent2)

    triggerTime = System.currentTimeMillis()+THIRD_ALARM
    AlarmManagerCompat.setExactAndAllowWhileIdle(alarmManager,
    AlarmManager.RTC_WAKEUP,triggerTime,notifyPendingIntent3)
}

fun cancelAllAlarms(app:Context){
    setPendingIntents(app)
    val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.cancel(notifyPendingIntent1)
    alarmManager.cancel(notifyPendingIntent2)
    alarmManager.cancel(notifyPendingIntent3)
}

private fun setPendingIntents(app:Context){
    val notifyIntent = Intent(app, AlarmReceiver::class.java)
    notifyPendingIntent1 = PendingIntent.getBroadcast(
        app,
        REQUEST_CODE_FIRST_ALARM,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    notifyPendingIntent2 = PendingIntent.getBroadcast(
        app,
        REQUEST_CODE_SECOND_ALARM,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    notifyPendingIntent3 = PendingIntent.getBroadcast(
        app,
        REQUEST_CODE_THIRD_ALARM,
        notifyIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}
