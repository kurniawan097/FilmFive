package com.cuursoft.favoriteapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(val listItems: MutableList<Movie>, private val context: Context,
                   private val mListener: OnItemClicked)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItems!![position]
        holder.tvJudul.text = item.title
        holder.tvDeskripsi.text = item.description
        Glide.with(context!!).load("https://image.tmdb.org/t/p/w500"+item.backdrop).into(holder.imgPoster)
        holder.ListMovie.setOnClickListener {
            mListener.onItemClick(position)
        }
    }


    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvJudul: TextView = itemView!!.findViewById(R.id.tv_judul)
        val tvDeskripsi: TextView = itemView!!.findViewById(R.id.tv_deskripsi)
        val imgPoster: ImageView = itemView!!.findViewById(R.id.img_poster)
        val ListMovie: RelativeLayout = itemView!!.findViewById(R.id.view_list)
    }

    interface OnItemClicked {
        fun onItemClick(position: Int)
    }
}