package com.neatflix.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.neatflix.R;
import com.neatflix.TrendingMoviesQuery;
import com.neatflix.app.BaseRecyclerAdapter;
import com.neatflix.app.Constants;
import com.neatflix.databinding.AdapterListBinding;

import java.util.List;

public class ListAdapter extends BaseRecyclerAdapter {
    Context context;
    List<TrendingMoviesQuery.TrendingMovie> list;

    public ListAdapter(Context context, List<TrendingMoviesQuery.TrendingMovie> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        AdapterListBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_list,group,false);
        return new ViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(context),parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setDataBind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        AdapterListBinding binding;
        public ViewHolder(AdapterListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

        }

        public void setDataBind(TrendingMoviesQuery.TrendingMovie trendingMovie) {
            Glide.with(context).load(Constants.BASE_POSTER_IMAGE_URL+trendingMovie.getBackdrop_path()).into(binding.imageIv);
            binding.nameTv.setText(trendingMovie.getTitle());
            binding.getRoot().setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("id",trendingMovie.getId());
                Navigation.findNavController(v).navigate(R.id.showDetailsFragment,bundle);
            });
        }
    }
}
