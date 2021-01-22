package com.alexaat.dailynews.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.alexaat.dailynews.R
import com.alexaat.dailynews.data.NewsItem
import com.alexaat.dailynews.util.milliToDate
import com.bumptech.glide.Glide

@BindingAdapter("setThumbnail")
fun ImageView.setThumbnail(newsItem: NewsItem){
    val url = newsItem.thumbnail
    val imgUri = url.toUri().buildUpon().scheme("https").build()
    Glide.with(context)
        .load(imgUri)
        .placeholder(R.drawable.loading_spinner)
        .error(R.drawable.ic_broken_image)
        .into(this)
}

@BindingAdapter("setTitle")
fun TextView.setTitle(newsItem: NewsItem){
    text = newsItem.webTitle
}
@BindingAdapter("setDate")
fun TextView.setDate(newsItem: NewsItem){
    text = milliToDate(newsItem.webPublicationDate)


}