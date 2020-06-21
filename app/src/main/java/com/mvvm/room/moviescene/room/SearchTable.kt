package com.mvvm.room.moviescene.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SearchDetails")
data class SearchTable (

    @ColumnInfo(name = "MovieId")
    public val IMDBid: String? = null,

    @ColumnInfo(name = "MovieTitle")
    public val Title: String? = null,

    @ColumnInfo(name = "MovieYear")
    public val Year: String? = null,

    @ColumnInfo(name = "MoviePoster")
    public val Poster: String? = null,

    @ColumnInfo(name = "MovieType")
    public val Type: String? = null

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}