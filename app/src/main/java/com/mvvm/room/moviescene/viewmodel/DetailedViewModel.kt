package com.mvvm.room.moviescene.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.room.moviescene.model.DetailedResponse
import com.mvvm.room.moviescene.model.SearchResponse
import com.mvvm.room.moviescene.repository.DetailedRepository
import com.mvvm.room.moviescene.repository.SearchRepository
import com.mvvm.room.moviescene.room.DetailedTable
import com.mvvm.room.moviescene.room.SearchTable

class DetailedViewModel : ViewModel() {

    lateinit var detailedLiveData: MutableLiveData<DetailedResponse>
    lateinit var detailedTableLiveData: LiveData<DetailedTable>

    fun getDetailedData(context: Context, title: String) : MutableLiveData<DetailedResponse> {
        detailedLiveData = MutableLiveData()
        detailedLiveData = DetailedRepository.getDetailDataFromApi(detailedLiveData, context, title)
        return detailedLiveData
    }

    fun getDetailedDataFromDB(context: Context, title: String) : LiveData<DetailedTable> {
        detailedTableLiveData = MutableLiveData()
        detailedTableLiveData = DetailedRepository.getDetailsDataFromDB(context, title)
        return detailedTableLiveData
    }
}