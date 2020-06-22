package com.cuursoft.filmsubmitfive.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import android.os.HandlerThread
import android.database.ContentObserver
import android.content.Context
import android.database.Cursor
import android.os.Handler
import com.cuursoft.filmsubmitfive.DetailActivity
import com.cuursoft.filmsubmitfive.Movie
import com.cuursoft.filmsubmitfive.MovieAdapter
import com.cuursoft.filmsubmitfive.R
import com.cuursoft.filmsubmitfive.database.DatabaseContract
import com.cuursoft.filmsubmitfive.database.MovieHelper
import com.cuursoft.filmsubmitfive.helper.MappingHelper


class FavoriteMovieFragment : Fragment() {

    lateinit var movieHelper: MovieHelper
    lateinit var favoriteMovies: ArrayList<Movie>

    private var handlerThread: HandlerThread? = null
    private var myObserver: DataObserver? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieHelper = MovieHelper.getInstance(context)
        movieHelper.open()

        handlerThread = HandlerThread("DataObserver")
        handlerThread?.start()
        val handler = Handler(handlerThread?.looper)
        myObserver = DataObserver(handler, context as Context)
        context?.contentResolver?.registerContentObserver(
            DatabaseContract.MovieColumns().CONTENT_URI,
            true, myObserver as ContentObserver)

        val list = context?.contentResolver?.query(DatabaseContract.MovieColumns().CONTENT_URI, null,
            null, null, null)

        favoriteMovies = MappingHelper().mapCursorToArrayList(list as Cursor)

        recyclerV_favorite_movie.apply {
            adapter = MovieAdapter(
                favoriteMovies,
                context,
                object : MovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int) {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("extra_type", "movie_favorite")
                        intent.putExtra("fav_movie", favoriteMovies[position])
                        startActivity(intent)
                    }
                })

            val reverseLinearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,
                true)
            reverseLinearLayoutManager.stackFromEnd = true
            layoutManager = reverseLinearLayoutManager

        }
        progressBar_favorite_movie.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        favoriteMovies.clear()
        favoriteMovies.addAll(movieHelper.getAllMovie())
        recyclerV_favorite_movie.adapter?.notifyDataSetChanged()
    }

    class DataObserver(handler: Handler, internal val context: Context) : ContentObserver(handler)
}
