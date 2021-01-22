package com.alexaat.dailynews.util

import com.alexaat.dailynews.util.Constants.dateToMilliPatten
import com.alexaat.dailynews.util.Constants.milliToDatePatten
import java.text.SimpleDateFormat
import java.util.*

fun dateToMilli(date:String):Long{
    val simpleDateFormat = SimpleDateFormat(dateToMilliPatten, Locale.UK)
    val d = simpleDateFormat.parse(date)
    d?.let{
        return d.time
    }
    return System.currentTimeMillis()
}

fun milliToDate(milliSeconds:Long):String{
    val simpleDateFormat = SimpleDateFormat(milliToDatePatten, Locale.UK)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return simpleDateFormat.format(calendar.time)

}

fun getCutOffDate():Long{
    val days = 3
    val interval = days*24*60*60*1000
    return System.currentTimeMillis()-interval
}