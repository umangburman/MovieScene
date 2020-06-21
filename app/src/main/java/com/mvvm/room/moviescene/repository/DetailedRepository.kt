package com.mvvm.room.moviescene.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.room.moviescene.model.DetailedResponse
import com.mvvm.room.moviescene.model.SearchResponse
import com.mvvm.room.moviescene.others.Constants
import com.mvvm.room.moviescene.retrofit.RetrofitClient
import com.mvvm.room.moviescene.room.DetailedTable
import com.mvvm.room.moviescene.room.MovieDatabase
import com.mvvm.room.moviescene.room.SearchTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailedRepository {

    companion object {

        var movieDatabase: MovieDatabase? = null

        lateinit var detailedData: MutableLiveData<DetailedResponse>
        lateinit var detailedTableData: LiveData<DetailedTable>

        fun initializeDB(context: Context) : MovieDatabase {
            return MovieDatabase.getDataseClient(context)
        }

        fun getDetailDataFromApi(
            detailedLiveData: MutableLiveData<DetailedResponse>, context: Context, title: String
        ) : MutableLiveData<DetailedResponse> {

            detailedData = detailedLiveData

            val call = RetrofitClient.apiInterface.getDetailedData(
                "full", Constants.API_KEY, title
            )

            call.enqueue(object : Callback<DetailedResponse> {

                override fun onFailure(call: Call<DetailedResponse>, t: Throwable) {
                    // TODO("Not yet implemented")
                    detailedData.value = null
                }

                override fun onResponse(
                    call: Call<DetailedResponse>,
                    response: Response<DetailedResponse>
                ) {
                    // TODO("Not yet implemented")

                    movieDatabase = initializeDB(context)

                    val responseData : DetailedResponse = response.body()!!

                    val title = responseData.Title
                    val year = responseData.Year
                    val rated = responseData.Rated
                    val released = responseData.Released
                    val genre = responseData.Genre
                    val plot = responseData.Plot
                    val language = responseData.Language
                    val poster = responseData.Poster
                    val details = DetailedTable(rated, title, year, poster, released, genre, plot, language)
                    CoroutineScope(Dispatchers.IO).launch {
                        SearchRepository.movieDatabase!!.movieDao().insertDetailsDB(details)
                    }

                    detailedData.value = response.body()
                }
            })

            return detailedLiveData
        }

        fun getDetailsDataFromDB(context: Context, title: String
        ) : LiveData<DetailedTable> {

            movieDatabase = initializeDB(context)

            detailedTableData = movieDatabase!!.movieDao().getAllDetails("%$title%")

            return detailedTableData
        }
    }

}