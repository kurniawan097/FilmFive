package com.cuursoft.favoriteapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*

class MainViewModel : ViewModel() {
    private val listMovie = MutableLiveData<ArrayList<Movie>>()
    private val listTvMovie = MutableLiveData<ArrayList<TvMovie>>()

    internal val movie: LiveData<ArrayList<Movie>>
        get() = listMovie

    internal val tvMovie: LiveData<ArrayList<TvMovie>>
        get() = listTvMovie

    internal fun setMovieTvMovie(type: String) {
        val listItemsMovie = ArrayList<Movie>()
        val listItemsTvMovie = ArrayList<TvMovie>()

        val params = RequestParams()
        params.put("api_key", BuildConfig.TMDB_API_KEY)
        params.put("language", "en-US")

        val client = AsyncHttpClient()


        var url =  BuildConfig.URL_DISCOVER+"/$type"

        client.get(url, params, object : AsyncHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        if (type == "movie"){
                            val movie = list.getJSONObject(i)
                            val movieItems = Movie(movie)
                            listItemsMovie.add(movieItems)
                            listMovie.postValue(listItemsMovie)

                        }
                        else if (type == "tv"){
                            val tvMovie = list.getJSONObject(i)
                            val tvMovieItems = TvMovie(tvMovie)
                            listItemsTvMovie.add(tvMovieItems)
                            listTvMovie.postValue(listItemsTvMovie)

                        }

                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message)
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message)
            }
        })
    }

    internal fun searchMovieTvMovie(type: String, query: String) {
        val listItemsMovie = ArrayList<Movie>()
        val listItemsTvMovie = ArrayList<TvMovie>()

        val params = RequestParams()
        params.put("api_key", BuildConfig.TMDB_API_KEY)
        params.put("language", "en-US")
        params.put("query", query)
        val client = AsyncHttpClient()
        val url = BuildConfig.URL_SEARCH+"/$type"
        client.get(url, params, object : AsyncHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    if (list.length() < 1){
                        if (type == "movie"){
                            listMovie.postValue(null)
                        }
                        else if (type == "tv"){
                            listTvMovie.postValue(null)
                        }
                    }
                    else{
                        for (i in 0 until list.length()) {
                            if (type == "movie"){
                                val movie = list.getJSONObject(i)
                                val movieItems = Movie(movie)
                                listItemsMovie.add(movieItems)
                                listMovie.postValue(listItemsMovie)

                            }
                            else if (type == "tv"){
                                val tvMovie = list.getJSONObject(i)
                                val tvMovieItems = TvMovie(tvMovie)
                                listItemsTvMovie.add(tvMovieItems)
                                listTvMovie.postValue(listItemsTvMovie)

                            }

                        }
                    }

                } catch (e: Exception) {
                    Log.d("Exception", e.message)
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message)
            }
        })
    }

}
