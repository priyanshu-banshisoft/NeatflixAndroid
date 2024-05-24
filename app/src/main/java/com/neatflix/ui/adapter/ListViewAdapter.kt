package com.neatflix.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neatflix.PopularMoviesQuery
import com.neatflix.R
import com.neatflix.app.BaseRecyclerAdapter
import com.neatflix.app.Constants
import com.neatflix.databinding.AdapterPopularBinding


class ListViewAdapter(val context: Context, private val popularMovies: MutableList<PopularMoviesQuery.PopularMovie>) : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(context),parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder : ViewHolder = holder as ViewHolder
        viewHolder.setDataBind(popularMovies[position])

    }

    override fun getItemCount(): Int {
        return popularMovies.size
    }

    override fun getViewHolder(
        inflater: LayoutInflater?,
        group: ViewGroup?
    ): RecyclerView.ViewHolder {
        val binding : AdapterPopularBinding = DataBindingUtil.inflate(inflater!!, R.layout.adapter_popular,group,false)
        return ViewHolder(binding)
    }
    inner class ViewHolder(itemView: AdapterPopularBinding) : RecyclerView.ViewHolder(itemView.root) {
        fun setDataBind(popularMovie: PopularMoviesQuery.PopularMovie) {
            Glide.with(context).load(Constants.BASE_POSTER_IMAGE_URL+popularMovie.poster_path).into(binding.imageIv)
            binding.imageIv.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id", popularMovie.id!!)
                Navigation.findNavController(it).navigate(R.id.showDetailsFragment,bundle)
            }
        }

        val binding : AdapterPopularBinding

        init {
            binding = itemView
        }

    }
}