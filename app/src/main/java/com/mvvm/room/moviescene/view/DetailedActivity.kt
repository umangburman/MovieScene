package com.mvvm.room.moviescene.view

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mvvm.room.moviescene.R
import com.mvvm.room.moviescene.others.Connectivity
import com.mvvm.room.moviescene.viewmodel.DetailedViewModel
import kotlinx.android.synthetic.main.activity_detailed.*

class DetailedActivity : AppCompatActivity() {

    lateinit var detailedViewModel: DetailedViewModel

    lateinit var context: Context

    lateinit var strTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        context = this@DetailedActivity

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        strTitle = intent.getStringExtra("title")!!

        detailedViewModel = ViewModelProvider(this).get(DetailedViewModel::class.java)

        DetailWp10progressBar.showProgressBar()

        if (Connectivity.isOnline(context)) {

            detailedViewModel.getDetailedData(context, strTitle).observe(this, Observer { detailedResponse ->

                DetailWp10progressBar.hideProgressBar()

                if (detailedResponse == null) {
                    scrollViews.visibility = View.GONE
                    noDataConstraint.visibility = View.VISIBLE
                }
                else {

                    scrollViews.visibility = View.VISIBLE
                    noDataConstraint.visibility = View.GONE

                    val url = detailedResponse.Poster
                    Glide.with(this@DetailedActivity)
                        .load(url)
                        .centerCrop()
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_no_image_found)
                        .into(imgPoster)

                    lblMovieTitle.text = detailedResponse.Title
                    lblYear.text = detailedResponse.Year
                    lblPlot.text = detailedResponse.Plot
                    lblReleasedAnswer.text = detailedResponse.Released
                    lblGenreAnswer.text = detailedResponse.Genre
                    lblLanguageAnswer.text = detailedResponse.Language
                }

            })
        }
        else {

            detailedViewModel.getDetailedDataFromDB(context, strTitle).observe(this, Observer { detailedTableData ->

                DetailWp10progressBar.hideProgressBar()

                if (detailedTableData == null) {
                    scrollViews.visibility = View.GONE
                    noDataConstraint.visibility = View.VISIBLE
                }
                else {

                    scrollViews.visibility = View.VISIBLE
                    noDataConstraint.visibility = View.GONE

                    val url = detailedTableData.Poster
                    Glide.with(this@DetailedActivity)
                        .load(url)
                        .centerCrop()
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_no_image_found)
                        .into(imgPoster)

                    lblMovieTitle.text = detailedTableData.Title
                    lblYear.text = detailedTableData.Year
                    lblPlot.text = detailedTableData.Plot
                    lblReleasedAnswer.text = detailedTableData.Released
                    lblGenreAnswer.text = detailedTableData.Genre
                    lblLanguageAnswer.text = detailedTableData.Language
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}