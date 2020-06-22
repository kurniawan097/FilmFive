package com.cuursoft.filmsubmitfive.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.cuursoft.filmsubmitfive.R
import com.cuursoft.filmsubmitfive.SectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SectionsPagerAdapter(activity?.supportFragmentManager as FragmentManager)
        adapter.addFragment(FavoriteMovieFragment(), getString(R.string.movie))
        adapter.addFragment(FavoriteTvMovieFragment(), getString(R.string.tv_movie))

        view_pager_favorite.adapter = adapter
        tab_layout_favorite.setupWithViewPager(view_pager_favorite)

    }


}
