package com.cuursoft.filmsubmitfive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_release_today.*

class ReleaseMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release_today)

        supportActionBar?.title = getString(R.string.today_release)

        progressBar_release_today.visibility = View.VISIBLE

        if (intent.getSerializableExtra("movieList") != null) {
            showMovie()
        }


    }

    fun showMovie() {
        val movies = intent.getSerializableExtra("movieList") as ArrayList<Movie>
        recyclerV_release_today.apply {
            adapter = MovieAdapter(movies, applicationContext, object : MovieAdapter.OnItemClicked {
                override fun onItemClick(position: Int) {
                    val intent = Intent(applicationContext, DetailActivity::class.java)
                    intent.putExtra(DetailActivity().EXTRA_MOVIE_ID, movies[position].id)
                    intent.putExtra(DetailActivity().EXTRA_TYPE, "movie")
                    startActivity(intent)
                }
            })

            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        }
        progressBar_release_today.visibility = View.GONE

    }


}
