package com.cuursoft.filmsubmitfive

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import com.cuursoft.filmsubmitfive.favorite.FavoriteFragment
import com.cuursoft.filmsubmitfive.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var selectedFragmentName = "home"
    private val alarmReceiver = AlarmReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0.0f

        val sharedPref = SharedPrefManager(applicationContext).getInstance(applicationContext)


        if (sharedPref.checkInit() == 0){
            sharedPref.setDailyReminder(true)
            sharedPref.setReleaseReminder(true)
            alarmReceiver.setRepeatingAlarm(applicationContext, AlarmReceiver().TYPE_DAILY, "07:00",
                getString(R.string.daily_notif_message))
            alarmReceiver.setRepeatingAlarm(applicationContext, AlarmReceiver().TYPE_RELEASE, "08:00",
                getString(R.string.release_notif_message))

        }
        if (intent.extras != null) {
            val type = intent.getStringExtra("type")
            if (type == "release"){
                val intent = Intent(applicationContext, ReleaseMovieActivity::class.java)
                intent.putExtra("movieList", this.intent.getSerializableExtra("movieList"))
                Log.d("tes123", "movieList main = ${this.intent.getSerializableExtra("movieList")}")

                startActivity(intent)
            }
        }
        loadFragment(HomeFragment())

        bottom_nav_main.setOnNavigationItemSelectedListener {
            when{
                it.itemId == R.id.menu_bottom_nav_home ->
                {
                    selectedFragmentName = "home"
                    loadFragment(HomeFragment())
                }
                it.itemId == R.id.menu_bottom_nav_favorite ->
                {
                    selectedFragmentName = "favorite"
                    loadFragment(FavoriteFragment())
                }

                else ->
                {
                    selectedFragmentName = "home"
                    loadFragment(HomeFragment())
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchManager != null){
            val searchView = (menu?.findItem(R.id.action_search_main)?.actionView) as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = resources.getString(R.string.search_hint)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, SearchMovieActivity::class.java)
                    intent.putExtra(SearchMovieActivity().EXTRA_QUERY, query)
                    startActivity(intent)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_settings){
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment).commit()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("selectedFragmentName", selectedFragmentName)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        selectedFragmentName = savedInstanceState?.getString("selectedFragmentName").toString()

        if (selectedFragmentName == "home"){
            loadFragment(HomeFragment())
            bottom_nav_main.selectedItemId = R.id.menu_bottom_nav_home
        }
        else if (selectedFragmentName == "favorite"){
            loadFragment(FavoriteFragment())
            bottom_nav_main.selectedItemId = R.id.menu_bottom_nav_favorite

        }
    }
}
