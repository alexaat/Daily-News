package com.alexaat.dailynews.data.source.remote

import com.alexaat.dailynews.util.api.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ContentApiService{
    @GET("search?api-key=$API_KEY&format=json&show-fields=thumbnail&page-size=7")
    fun getServerRequestResultAsync(): Deferred<ServerRequestResult>

    @GET("search?api-key=$API_KEY&format=json&show-fields=thumbnail&page-size=1")
    fun getLastAsync(): Deferred<ServerRequestResult>

}
