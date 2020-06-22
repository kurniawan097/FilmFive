package com.cuursoft.favoriteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cuursoft.favoriteapp.R
import com.cuursoft.favoriteapp.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0.0f

        supportActionBar?.elevation = 0.0f

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f

    }
}
