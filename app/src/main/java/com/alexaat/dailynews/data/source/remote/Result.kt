package com.alexaat.dailynews.data.source.remote

class Result(
    val id:String,
    val sectionId:String,
    val sectionName:String,
    val webPublicationDate:String,
    val webTitle:String,
    val webUrl:String,
    val apiUrl:String,
    val fields: Fields? = null,
    val isHosted:Boolean,
    val pillarId:String,
    val pillarName:String)