package com.cuursoft.filmsubmitfive

import android.content.Context
import android.content.SharedPreferences


class SharedPrefManager(val context: Context) {


    private val SHARED_PREF_NAME = "submission5madesharedpref"
    private val KEY_DAILY = "daily_reminder"
    private val KEY_RELEASE = "release_reminder"
    private val KEY_INIT = "init"

    private var mInstance: SharedPrefManager? = null
    private val sharedPreferences: SharedPreferences?
            = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)



    @Synchronized
    fun getInstance(context: Context): SharedPrefManager {
        if (mInstance == null) {
            mInstance = SharedPrefManager(context)
        }
        return mInstance as SharedPrefManager
    }

    fun setDailyReminder(daily: Boolean){
        val editor = sharedPreferences?.edit()

        editor?.putBoolean(KEY_DAILY, daily)
        editor?.putInt(KEY_INIT, 1)
        editor?.apply()
    }
    fun setReleaseReminder(release: Boolean){
        val editor = sharedPreferences?.edit()

        editor?.putBoolean(KEY_RELEASE, release)
        editor?.putInt(KEY_INIT, 1)
        editor?.apply()
    }


    fun checkDailyReminder() : Boolean?{
        return sharedPreferences?.getBoolean(KEY_DAILY, true)
    }

    fun checkReleaseReminder() : Boolean?{
        return sharedPreferences?.getBoolean(KEY_RELEASE, true)
    }

    fun checkInit(): Int? {
        return sharedPreferences?.getInt(KEY_INIT,0)
    }
}