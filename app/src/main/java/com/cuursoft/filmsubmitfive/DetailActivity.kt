package com.cuursoft.filmsubmitfive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.cuursoft.filmsubmitfive.database.MovieHelper
import com.cuursoft.filmsubmitfive.database.TvMovieHelper
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    lateinit var movie: Movie
    lateinit var tvMovie: TvMovie
    lateinit var movieHelper: MovieHelper
    lateinit var tvMovieHelper: TvMovieHelper
    lateinit var type: String
    var isFavorite = false
    var isLoaded = false

    val EXTRA_TYPE = "extra_type"
    val EXTRA_MOVIE_ID = "extra_movie_id"
    val EXTRA_TV_MOVIE_ID = "extra_tv_movie_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar_detailmovie.visibility = View.VISIBLE
        type = intent.getStringExtra(EXTRA_TYPE)

        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()

        tvMovieHelper = TvMovieHelper.getInstance(applicationContext)
        tvMovieHelper.open()


        val params = RequestParams()
        params.put("api_key", BuildConfig.TMDB_API_KEY)
        params.put("language", "en-US")
        val client = AsyncHttpClient()

        //get parcelable object from intent
        when (type) {
            "movie" -> {
                val id = intent.getStringExtra(EXTRA_MOVIE_ID)
                val url = BuildConfig.URL_DETAIL_MOVIE+"/$id"

                Log.d("TES123", "id = " + id)
                client.get(url, params, object : JsonHttpResponseHandler() {

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                        try {
                            movie = Movie(response as JSONObject)
                            tv_rilis.text = getString(R.string.release_date)
                            tv_skor.text = getString(R.string.user_score)
                            tv_desk.text = getString(R.string.overview)

                            tv_judul_detail.text = movie.title
                            tv_tanggal_rilis.text = movie.releaseDate
                            tv_nilai_skor.text = movie.score
                            tv_dekripsi_detail.text = movie.description

                            Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + movie.poster)
                                .into(poster_detail_movie)

                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }

                        isLoaded = true
                        progressBar_detailmovie.visibility = View.GONE
                        isFavorite = isFavorited()
                        invalidateOptionsMenu()
                    }

                    override fun onFailure( statusCode: Int,
                                            headers: Array<out Header>?,
                                            throwable: Throwable?,
                                            errorResponse: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, errorResponse?.getString("status_message"), Toast.LENGTH_SHORT).show()
                    }

                })
            }
            "tv" -> {
                val id = intent.getStringExtra(EXTRA_TV_MOVIE_ID)
                val url = BuildConfig.URL_DETAIL_TV_MOVIE+"/$id"

                client.get(url, params, object : JsonHttpResponseHandler() {

                    override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONObject?) {
                        try {
                            tvMovie = TvMovie(response as JSONObject)
                            tv_rilis.text = getString(R.string.first_air_date)
                            tv_skor.text = getString(R.string.user_score)
                            tv_desk.text = getString(R.string.overview)

                            tv_judul_detail.text = tvMovie.title
                            tv_tanggal_rilis.text = tvMovie.releaseDate
                            tv_nilai_skor.text = tvMovie.score
                            tv_dekripsi_detail.text = tvMovie.description

                            Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + tvMovie.poster)
                                .into(poster_detail_movie)

                        } catch (e: Exception) {
                            Log.d("Exception", e.message)
                        }

                        progressBar_detailmovie.visibility = View.GONE

                        isLoaded = true
                        progressBar_detailmovie.visibility = View.GONE
                        isFavorite = isFavorited()
                        invalidateOptionsMenu()

                    }

                    override fun onFailure(statusCode: Int,
                                           headers: Array<out Header>?,
                                           throwable: Throwable?,
                                           errorResponse: JSONObject?
                    ) {
                        Toast.makeText(applicationContext, errorResponse?.getString("status_message"), Toast.LENGTH_SHORT)

                    }

                })
            }
            "movie_favorite" -> {
                movie = intent.getParcelableExtra<Movie>("fav_movie")
                tv_rilis.text = getString(R.string.release_date)
                tv_skor.text = getString(R.string.user_score)
                tv_desk.text = getString(R.string.overview)

                tv_judul_detail.text = movie.title
                tv_tanggal_rilis.text = movie.releaseDate
                tv_nilai_skor.text = movie.score
                tv_dekripsi_detail.text = movie.description

                Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + movie.poster)
                    .into(poster_detail_movie)
                progressBar_detailmovie.visibility = View.GONE

                isLoaded = true
                progressBar_detailmovie.visibility = View.GONE
                isFavorite = isFavorited()
                invalidateOptionsMenu()

            }
            "tv_movie_favorite" -> {
                tvMovie = intent.getParcelableExtra<TvMovie>("fav_tv_movie")
                tv_rilis.text = getString(R.string.first_air_date)
                tv_skor.text = getString(R.string.user_score)
                tv_desk.text = getString(R.string.overview)

                tv_judul_detail.text = tvMovie.title
                tv_tanggal_rilis.text = tvMovie.releaseDate
                tv_nilai_skor.text = tvMovie.score
                tv_dekripsi_detail.text = tvMovie.description

                Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + tvMovie.poster)
                    .into(poster_detail_movie)
                progressBar_detailmovie.visibility = View.GONE

                isLoaded = true
                progressBar_detailmovie.visibility = View.GONE
                isFavorite = isFavorited()
                invalidateOptionsMenu()


            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_add_to_favorite -> {
                if (isFavorite)
                {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
                    if (type == "movie" || type == "movie_favorite"){
                        val result = movieHelper.deleteMovie(movie.id)
                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.remove_movie_from_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }
                    else if (type == "tv" || type == "tv_movie_favorite"){
                        val result = tvMovieHelper.deleteTvMovie(tvMovie.id)
                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.remove_tv_from_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }



                }
                else{
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)

                    if (type == "movie" || type == "movie_favorite"){
                        val result = movieHelper.insertMovie(movie)

                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.add_movie_to_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }
                    else if (type == "tv" || type == "tv_movie_favorite"){
                        val result = tvMovieHelper.insertTvMovie(tvMovie)

                        if (result > 0){
                            Toast.makeText(applicationContext, getString(R.string.add_tv_show_to_favorite_success),
                                Toast.LENGTH_SHORT).show()
                            isFavorite = isFavorited()
                        }
                    }

                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun isFavorited(): Boolean{
        if (type == "movie" || type == "movie_favorite"){
            if (movieHelper.isMovieFavorited(movie.id)){
                return true
            }
        }
        else if(type == "tv" || type == "tv_movie_favorite"){
            if (tvMovieHelper.isTvMovieFavorited(tvMovie.id)){
                return true
            }
        }

        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (isLoaded){
            menu?.getItem(0)?.isVisible = true

        }
        if (isFavorite)
        {
            //menu?.getItem(0)?.isVisible = true
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        }
        else{
            //menu?.getItem(0)?.isVisible = true
            menu?.getItem(0)?.icon = ContextCompat
                .getDrawable(this, R.drawable.ic_favorite_border_black_24dp)

        }
        return super.onPrepareOptionsMenu(menu)
    }

}
