package com.mvvm.room.moviescene.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetails")
class DetailedTable (

    @ColumnInfo(name = "Rated")
    public val Rated: String? = null,

    @ColumnInfo(name = "MovieTitle")
    public val Title: String? = null,

    @ColumnInfo(name = "MovieYear")
    public val Year: String? = null,

    @ColumnInfo(name = "MoviePoster")
    public val Poster: String? = null,

    @ColumnInfo(name = "MovieReleased")
    public val Released: String? = null,

    @ColumnInfo(name = "MovieGenre")
    public val Genre: String? = null,

    @ColumnInfo(name = "MoviePlot")
    public val Plot: String? = null,

    @ColumnInfo(name = "MovieLanguage")
    public val Language: String? = null

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}