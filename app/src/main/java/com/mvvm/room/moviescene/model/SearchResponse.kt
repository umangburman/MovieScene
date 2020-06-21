package com.mvvm.room.moviescene.model

data class SearchResponse(

    val Search: ArrayList<SearchContent>
)

data class SearchContent(

    val Title: String? = null,
    val Year: String? = null,
    val imdbID: String? = null,
    val Type: String? = null,
    val Poster: String? = null
)
