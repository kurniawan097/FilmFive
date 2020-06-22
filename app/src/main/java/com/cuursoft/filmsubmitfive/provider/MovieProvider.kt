package com.cuursoft.filmsubmitfive.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.os.Handler
import com.cuursoft.filmsubmitfive.database.DatabaseContract
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.ID
import com.cuursoft.filmsubmitfive.database.MovieHelper
import com.cuursoft.filmsubmitfive.favorite.FavoriteMovieFragment

class MovieProvider : ContentProvider() {

    private val MOVIE = 1
    private val MOVIE_ID = 2
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    lateinit var movieHelper: MovieHelper


    override fun onCreate(): Boolean {
        UriMatching()
        movieHelper = MovieHelper(context)

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        movieHelper.open()
        val added: Long
        when (sUriMatcher.match(uri)) {
            MOVIE -> added = movieHelper.insertProvider(values as ContentValues)
            else -> added = 0
        }
        context?.contentResolver?.notifyChange(
            DatabaseContract.MovieColumns().CONTENT_URI,
            FavoriteMovieFragment.DataObserver(Handler(), context))
        return Uri.parse(DatabaseContract.MovieColumns().CONTENT_URI.toString()+ "/" + added)
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        movieHelper.open()
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            MOVIE -> cursor = movieHelper.queryProvider()
            MOVIE_ID -> cursor = movieHelper.queryByIdProvider(uri.lastPathSegment as String)
            else -> cursor = null
        }
        return cursor
    }


    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        movieHelper.open()
        val updated: Int
        when (sUriMatcher.match(uri)) {
            MOVIE_ID -> updated = movieHelper.updateProvider(uri.lastPathSegment as String, values as ContentValues)
            else -> updated = 0
        }
        context!!.contentResolver.notifyChange(DatabaseContract.MovieColumns().CONTENT_URI,
            FavoriteMovieFragment.DataObserver(Handler(), context))
        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        movieHelper.open()
        val deleted: Int
        when (sUriMatcher.match(uri)) {
            MOVIE_ID -> deleted = movieHelper.deleteProvider(uri.lastPathSegment)
            else -> deleted = 0
        }
        context!!.contentResolver.notifyChange(
            DatabaseContract.MovieColumns().CONTENT_URI,
            FavoriteMovieFragment.DataObserver(Handler(), context))
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    private fun UriMatching(){
        sUriMatcher.addURI(DatabaseContract().AUTHORITY, DatabaseContract().TABLE_MOVIE, MOVIE)
        sUriMatcher.addURI(DatabaseContract().AUTHORITY, DatabaseContract().TABLE_MOVIE + "/$ID", MOVIE_ID)

    }
}