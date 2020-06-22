package com.cuursoft.filmsubmitfive

import android.database.Cursor
import android.os.Parcelable
import com.cuursoft.filmsubmitfive.database.DatabaseContract
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.BACKDROP
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.DESCRIPTION
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.ID
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.POSTER
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.RELEASE_DATE
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.SCORE
import com.cuursoft.filmsubmitfive.database.DatabaseContract.MovieColumns.Companion.TITLE
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

@Parcelize
data class Movie (
    var id: String,
    var title: String,
    var releaseDate: String,
    var score: String,
    var description: String,
    var poster: String,
    var backdrop: String
) : Parcelable{

    constructor(`object`: JSONObject) : this(
        `object`.getString("id"),
        `object`.getString("title"),
        `object`.getString("release_date"),
        `object`.getString("vote_average"),
        `object`.getString("overview"),
        `object`.getString("poster_path"),
        `object`.getString("backdrop_path")
    )

    constructor(cursor: Cursor) : this(
        DatabaseContract().getColumnString(cursor, ID),
        DatabaseContract().getColumnString(cursor, TITLE),
        DatabaseContract().getColumnString(cursor, RELEASE_DATE),
        DatabaseContract().getColumnString(cursor, SCORE),
        DatabaseContract().getColumnString(cursor, DESCRIPTION),
        DatabaseContract().getColumnString(cursor, POSTER),
        DatabaseContract().getColumnString(cursor, BACKDROP))


}