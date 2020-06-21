package com.mvvm.room.moviescene.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.room.moviescene.model.SearchContent
import com.mvvm.room.moviescene.model.SearchResponse
import com.mvvm.room.moviescene.others.Constants
import com.mvvm.room.moviescene.retrofit.RetrofitClient
import com.mvvm.room.moviescene.room.MovieDao
import com.mvvm.room.moviescene.room.MovieDatabase
import com.mvvm.room.moviescene.room.SearchTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository {

    companion object {

        var movieDatabase: MovieDatabase? = null

        lateinit var searchData: MutableLiveData<SearchResponse>
        lateinit var searchTableData: LiveData<List<SearchTable>>

        fun initializeDB(context: Context) : MovieDatabase {
            return MovieDatabase.getDataseClient(context)
        }

        fun getSearchDataFromApi(
            searchLiveData: MutableLiveData<SearchResponse>, context: Context, title: String
        ) : MutableLiveData<SearchResponse> {

            searchData = searchLiveData

            val call = RetrofitClient.apiInterface.getSearchData(
                "movie", Constants.API_KEY, 1, title
            )

            call.enqueue(object : Callback<SearchResponse> {

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    // TODO("Not yet implemented")
                    searchData.value = null
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    // TODO("Not yet implemented")

                    movieDatabase = initializeDB(context)

                    val searchResponseList = response.body()!!.Search

                    for (items in searchResponseList) {
                        val id = items.imdbID
                        val title = items.Title
                        val year = items.Year
                        val type = items.Type
                        val poster = items.Poster
                        val searchDetails = SearchTable(id, title, year, poster, type)
                        CoroutineScope(Dispatchers.IO).launch {
                            movieDatabase!!.movieDao().insertDetails(searchDetails)
                        }
                    }

                    searchData.value = response.body()
                }
            })

            return searchLiveData
        }

        fun getSearchDataFromDB(context: Context, title: String
        ) : LiveData<List<SearchTable>> {

            movieDatabase = initializeDB(context)

            searchTableData = movieDatabase!!.movieDao().getDetails("%$title%")

            return searchTableData
        }
    }

}