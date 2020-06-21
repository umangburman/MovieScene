package com.mvvm.room.moviescene.adapter.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvm.room.moviescene.R
import com.mvvm.room.moviescene.room.SearchTable
import com.mvvm.room.moviescene.view.DetailedActivity
import kotlinx.android.synthetic.main.list_item_search.view.*

class SearchAdapterDB(val context: Context, val movieList: List<SearchTable>) :
    RecyclerView.Adapter<SearchAdapterDB.SearchHolder>() {

    class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgPoster = itemView.imgImage
        var lblMovieTitle = itemView.lblMovieTitle
        var lblMovieId = itemView.lblMovieID
        var lblMovieYear = itemView.lblYear
        var lblMovieType = itemView.lblType
        var btnSelect = itemView.btnSelect
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_search, parent, false)

        return SearchHolder(v)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {

        val movieData = movieList.get(position)

        val url = movieData.Poster
        Glide.with(context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_no_image_found)
            .into(holder.imgPoster)

        holder.lblMovieTitle.text = movieData.Title
        holder.lblMovieId.text = movieData.IMDBid
        holder.lblMovieYear.text = movieData.Year
        holder.lblMovieType.text = movieData.Type

        holder.btnSelect.setOnClickListener {
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra("title", movieData.Title)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = movieList.size

}