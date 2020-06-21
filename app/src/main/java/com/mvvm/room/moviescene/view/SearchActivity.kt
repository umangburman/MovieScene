package com.mvvm.room.moviescene.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.mvvm.room.moviescene.R
import com.mvvm.room.moviescene.adapter.search.SearchAdapter
import com.mvvm.room.moviescene.adapter.search.SearchAdapterDB
import com.mvvm.room.moviescene.others.Connectivity
import com.mvvm.room.moviescene.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SearchActivity : AppCompatActivity() {

    lateinit var searchViewModel: SearchViewModel

    lateinit var context: Context

    var searchAdapter: SearchAdapter? = null
    var searchAdapterDB: SearchAdapterDB? = null

    lateinit var strTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@SearchActivity

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context, VERTICAL, false)

        innerConstraint.visibility = View.GONE

        btnSearch.setOnClickListener {

            txtSearch.onEditorAction(EditorInfo.IME_ACTION_DONE);

            strTitle = txtSearch.text.toString().trim()

            if (strTitle.length < 3) {
                txtSearch.setError(getString(R.string.enter_atleast_3_letters))
                return@setOnClickListener
            }

            if (Connectivity.isOnline(context)) {

                wp7progressBarTop.showProgressBar()

                searchViewModel.getSearchData(context, strTitle)
                    .observe(this, Observer { searchResponse ->

                        if (searchResponse == null) {

                            wp7progressBarTop.hideProgressBar()

                            imgNoData.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.ic_no_data_found
                                )
                            )
                            lblTypeHere.text = getString(R.string.no_movie_found)
                            innerConstraint.visibility = View.GONE
                            noDataConstraint.visibility = View.VISIBLE
                        } else {

                            wp7progressBarTop.hideProgressBar()

                            innerConstraint.visibility = View.VISIBLE
                            noDataConstraint.visibility = View.GONE

                            searchAdapter =
                                SearchAdapter(
                                    context,
                                    searchResponse.Search
                                )
                            recyclerView.adapter = searchAdapter
                        }
                    })
            } else {

                searchViewModel.getSearchDataFromDB(context, strTitle).observe(this,
                    Observer { searchTableResponse ->

                    wp7progressBarTop.showProgressBar()

                    if (searchTableResponse == null || searchTableResponse.isEmpty()) {

                        wp7progressBarTop.hideProgressBar()

                        imgNoData.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_no_data_found
                            )
                        )
                        lblTypeHere.text = getString(R.string.no_movie_found)
                        innerConstraint.visibility = View.GONE
                        noDataConstraint.visibility = View.VISIBLE
                    } else {

                        wp7progressBarTop.hideProgressBar()

                        innerConstraint.visibility = View.VISIBLE
                        noDataConstraint.visibility = View.GONE

                        searchAdapterDB =
                            SearchAdapterDB(
                                context,
                                searchTableResponse
                            )
                        recyclerView.adapter = searchAdapterDB
                    }
                })
            }
        }
    }
}