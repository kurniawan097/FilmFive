package com.cuursoft.filmsubmitfive.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuursoft.filmsubmitfive.*
import kotlinx.android.synthetic.main.fragment_tvmovie.*

class TvMovieFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: TvMovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvmovie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity as FragmentActivity).get(MainViewModel::class.java)
        mainViewModel.tvMovie.observe(this, getTvMovie)
        mainViewModel.setMovieTvMovie("tv")

        progressBar_tv_movie.visibility = View.VISIBLE

    }

    private val getTvMovie =
        Observer<ArrayList<TvMovie>> { tvMovie ->
            if (tvMovie != null) {
                mAdapter = TvMovieAdapter(tvMovie, context as Context,  object : TvMovieAdapter.OnItemClicked {
                    override fun onItemClick(position: Int){
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity().EXTRA_TV_MOVIE_ID, tvMovie[position].id)
                        intent.putExtra(DetailActivity().EXTRA_TYPE, "tv")
                        startActivity(intent)
                    }
                })

                recyclerV_tv_movie.apply {
                    adapter = mAdapter

                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }

                progressBar_tv_movie.visibility = View.GONE

            }
        }


}
