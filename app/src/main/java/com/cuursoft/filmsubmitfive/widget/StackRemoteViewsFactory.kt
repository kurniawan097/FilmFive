package com.cuursoft.filmsubmitfive.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.cuursoft.filmsubmitfive.Movie
import com.cuursoft.filmsubmitfive.R
import com.cuursoft.filmsubmitfive.database.MovieHelper


class StackRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = ArrayList<Movie>()
    lateinit var movieHelper: MovieHelper

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {

        val identityToken = Binder.clearCallingIdentity()

        movieHelper = MovieHelper.getInstance(context)
        movieHelper.open()
        mWidgetItems.clear()
        mWidgetItems.addAll(movieHelper.getAllMovie())

        Log.d("TES123", "fav movies size" + mWidgetItems.size.toString())

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        var poster = Glide.with(context)
            .asBitmap()
            .load("https://image.tmdb.org/t/p/w342" + mWidgetItems[position].poster)
            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()

        rv.setImageViewBitmap(R.id.imageView, poster)
        val extras = Bundle()
        extras.putInt(FavoriteMovieWidget().EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}