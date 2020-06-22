package com.cuursoft.favoriteapp.favorite


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuursoft.favoriteapp.DetailActivity
import com.cuursoft.favoriteapp.R
import com.cuursoft.favoriteapp.TvMovie
import com.cuursoft.favoriteapp.TvMovieAdapter
import com.cuursoft.favoriteapp.database.TvMovieHelper
import kotlinx.android.synthetic.main.fragment_favorite_tvmovie.*


class FavoriteTvMovieFragment : Fragment() {

    lateinit var tvMovieHelper: TvMovieHelper
    lateinit var favoriteTvMovie: ArrayList<TvMovie>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favorite_tvmovie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvMovieHelper = TvMovieHelper.getInstance(context)
        tvMovieHelper.open()

        favoriteTvMovie = tvMovieHelper.getAllTvMovie()

        recyclerV_favorite_tv_movie.apply {
            adapter = TvMovieAdapter(
                favoriteTvMovie,
                context,
                object : TvMovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("extra_type", "tv_movie_favorite")
                        intent.putExtra("fav_tv_movie", favoriteTvMovie[position])
                        startActivity(intent)
                    }
                })

            val reverseLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,
                true)
            reverseLinearLayoutManager.stackFromEnd = true
            layoutManager = reverseLinearLayoutManager

        }
        progressBar_favorite_tv_movie.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        favoriteTvMovie.clear()
        favoriteTvMovie.addAll(tvMovieHelper.getAllTvMovie())
        recyclerV_favorite_tv_movie.adapter?.notifyDataSetChanged()
    }
}
