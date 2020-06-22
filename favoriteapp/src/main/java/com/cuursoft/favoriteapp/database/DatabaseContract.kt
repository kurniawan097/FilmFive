package com.cuursoft.favoriteapp.database

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class DatabaseContract {
    var TABLE_MOVIE = "movie"
    var TABLE_TV_MOVIE = "tv_show"

    val AUTHORITY = "com.cuursoft.filmsubmitfive"
    private val SCHEME = "content"


    internal class MovieColumns : BaseColumns {
        companion object {
            var ID = "id"
            var TITLE = "title"
            var RELEASE_DATE = "release_date"
            var SCORE = "score"
            var DESCRIPTION = "description"
            var POSTER = "poster"
            var BACKDROP = "backdrop"
        }

        val CONTENT_URI = Uri.Builder().scheme(DatabaseContract().SCHEME)
            .authority(DatabaseContract().AUTHORITY)
            .appendPath(DatabaseContract().TABLE_MOVIE)
            .build()
    }

    fun getColumnString(cursor: Cursor, columnName: String): String {
        return cursor.getString(cursor.getColumnIndex(columnName))
    }


    internal class TvMovieColumns : BaseColumns {
        companion object {
            var ID = "id"
            var TITLE = "title"
            var FIRST_AIR_DATE ="first_air_date"
            var SCORE = "score"
            var DESCRIPTION = "description"
            var POSTER = "poster"
            var BACKDROP = "backdrop"

        }
        val CONTENT_URI_TV = Uri.Builder().scheme(DatabaseContract().SCHEME)
            .authority(DatabaseContract().AUTHORITY)
            .appendPath(DatabaseContract().TABLE_TV_MOVIE)
            .build()

    }
}