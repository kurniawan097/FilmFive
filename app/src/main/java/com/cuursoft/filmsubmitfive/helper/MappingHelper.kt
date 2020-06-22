package com.cuursoft.filmsubmitfive.helper

import android.database.Cursor
import com.cuursoft.filmsubmitfive.Movie
import com.cuursoft.filmsubmitfive.TvMovie
import com.cuursoft.filmsubmitfive.database.DatabaseContract
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.BACKDROP
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.DESCRIPTION
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.ID
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.POSTER
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.RELEASE_DATE
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.SCORE
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.TITLE
import com.cuursoft.filmsubmitfive.database.DatabaseContract.TvMovieColumns.Companion.FIRST_AIR_DATE


class MappingHelper {

    fun mapCursorToArrayList(moviesCursor: Cursor): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()
        while (moviesCursor.moveToNext()) {
            val id = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(ID))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE))
            val releaseDate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASE_DATE))
            val score = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(SCORE))
            val description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DESCRIPTION))
            val poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER))
            val backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BACKDROP))
            moviesList.add(Movie(id, title, releaseDate, score, description, poster, backdrop))
        }
        return moviesList
    }

    fun mapCursorToArrayTv(tvCursor: Cursor): ArrayList<TvMovie>{
        val tvList = ArrayList<TvMovie>()
        while (tvCursor.moveToNext()){
            val id = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvMovieColumns.ID))
            val title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvMovieColumns.TITLE))
            val firstAir = tvCursor.getString(tvCursor.getColumnIndexOrThrow(FIRST_AIR_DATE))
            val score = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvMovieColumns.SCORE))
            val description = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvMovieColumns.DESCRIPTION))
            val poster = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvMovieColumns.POSTER))
            val backdrop = tvCursor.getString(tvCursor.getColumnIndexOrThrow(DatabaseContract.TvMovieColumns.BACKDROP))
            tvList.add(TvMovie(id, title, firstAir, score, description, poster, backdrop))
        }
        return tvList
    }
}