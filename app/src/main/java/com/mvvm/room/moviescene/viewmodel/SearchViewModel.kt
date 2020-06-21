package com.mvvm.room.moviescene.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.room.moviescene.model.SearchResponse
import com.mvvm.room.moviescene.others.Connectivity
import com.mvvm.room.moviescene.repository.SearchRepository
import com.mvvm.room.moviescene.room.SearchTable

class SearchViewModel : ViewModel() {

    lateinit var searchLiveData: MutableLiveData<SearchResponse>
    lateinit var searchTableLiveData: LiveData<List<SearchTable>>

    fun getSearchData(context: Context, title: String) : MutableLiveData<SearchResponse> {
        searchLiveData = MutableLiveData()
        searchLiveData = SearchRepository.getSearchDataFromApi(searchLiveData, context, title)
        return searchLiveData
    }

    fun getSearchDataFromDB(context: Context, title: String) : LiveData<List<SearchTable>> {
        searchTableLiveData = MutableLiveData()
        searchTableLiveData = SearchRepository.getSearchDataFromDB(context, title)
        return searchTableLiveData
    }
}