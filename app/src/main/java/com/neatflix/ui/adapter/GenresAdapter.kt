package com.neatflix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neatflix.GenresMovieQuery
import com.neatflix.MovieQuery
import com.neatflix.R
import com.neatflix.app.BaseRecyclerAdapter
import com.neatflix.databinding.AdapterGenresBinding

class GenresAdapter(val context: Context, private val genres: MutableList<GenresMovieQuery.Genre>) : BaseRecyclerAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(context),parent)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val viewHolder : ViewHolder = holder as ViewHolder
        viewHolder.setDataBind(genres[position])

    }

    override fun getItemCount(): Int {
        return genres.size

    }

    override fun getViewHolder(
        inflater: LayoutInflater?,
        group: ViewGroup?
    ): RecyclerView.ViewHolder {
       val binding : AdapterGenresBinding = DataBindingUtil.inflate(inflater!!, R.layout.adapter_genres,group,false)
        return ViewHolder(binding)
    }

    class ViewHolder(itemView: AdapterGenresBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun setDataBind(genre: GenresMovieQuery.Genre) {
            binding.genresName.text = genre.name
        }

        val binding: AdapterGenresBinding

        init {
            binding = itemView
        }

    }

}