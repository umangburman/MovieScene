package com.mvvm.room.moviescene.retrofit

import com.mvvm.room.moviescene.model.DetailedResponse
import com.mvvm.room.moviescene.model.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET(".")
    fun getSearchData(

        @Query("type") Type: String,
        @Query("apikey") ApiKey: String,
        @Query("page") Page: Int,
        @Query("s") SearchText: String

    ) : Call<SearchResponse>

    @GET(".")
    fun getDetailedData (

        @Query("plot") Plot: String,
        @Query("apikey") ApiKey: String,
        @Query("t") Title: String

    ) : Call<DetailedResponse>
}