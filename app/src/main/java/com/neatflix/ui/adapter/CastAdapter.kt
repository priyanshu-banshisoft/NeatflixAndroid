package com.neatflix.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neatflix.CreditQuery
import com.neatflix.R
import com.neatflix.app.BaseRecyclerAdapter
import com.neatflix.app.Constants
import com.neatflix.databinding.AdapterCastBinding
import com.neatflix.utils.StringUtill

class CastAdapter(val context: Context,val cast: List<CreditQuery.Cast?>?) : BaseRecyclerAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(LayoutInflater.from(context),parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder : ViewHolder = holder as ViewHolder
        viewHolder.setDataBind(cast!![position])

    }

    override fun getItemCount(): Int {
        return cast!!.size

    }

    override fun getViewHolder(
        inflater: LayoutInflater?,
        group: ViewGroup?
    ): RecyclerView.ViewHolder {
       val binding : AdapterCastBinding = DataBindingUtil.inflate(inflater!!, R.layout.adapter_cast,group,false)
        return ViewHolder(binding)

    }

   inner class ViewHolder(itemView: AdapterCastBinding) : RecyclerView.ViewHolder(itemView.root){
       fun setDataBind(cast: CreditQuery.Cast?) {
           if (!StringUtill.isEmpty(cast!!.profile_path)) {
               Glide.with(context).load(Constants.BASE_POSTER_IMAGE_URL + cast.profile_path)
                   .into(binding.memberImg)
           }
           binding.memberNameTv.text = cast.name

       }
       val binding : AdapterCastBinding

       init {
           binding = itemView
       }

   }

}