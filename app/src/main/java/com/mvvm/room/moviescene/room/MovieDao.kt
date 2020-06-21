package com.mvvm.room.moviescene.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetails(data: SearchTable?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailsDB(data: DetailedTable?)

    @Query("select * from SearchDetails where MovieTitle like :title")
    fun getDetails(title: String?): LiveData<List<SearchTable>>

    @Query("select * from MovieDetails where MovieTitle like :title")
    fun getAllDetails(title: String?): LiveData<DetailedTable>
}