package com.neatflix.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.rx3.Rx3Apollo;
import com.google.gson.Gson;
import com.neatflix.GenresMovieQuery;
import com.neatflix.PopularMoviesQuery;
import com.neatflix.R;
import com.neatflix.TrendingMoviesQuery;
import com.neatflix.app.AppDelegate;
import com.neatflix.app.BaseApplication;
import com.neatflix.app.BaseFragment;
import com.neatflix.databinding.FragmentMovieBinding;
import com.neatflix.ui.adapter.GenresAdapter;
import com.neatflix.ui.adapter.ListAdapter;
import com.neatflix.ui.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieFragment extends BaseFragment {

    FragmentMovieBinding binding;
    ApolloClient apolloClient;
    ListAdapter listAdapter;
    GenresAdapter genresAdapter;
    ListViewAdapter listViewAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
            BaseApplication application = (BaseApplication) requireActivity().getApplication();
            apolloClient = application.getApolloClient();
            TrendingMoviesQuery trendingMoviesQuery = new TrendingMoviesQuery();
            GenresMovieQuery genresMovieQuery = new GenresMovieQuery();
            PopularMoviesQuery popularMoviesQuery = new PopularMoviesQuery();
            Rx3Apollo.single(apolloClient.query(genresMovieQuery)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApolloResponse<GenresMovieQuery.Data>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@NonNull ApolloResponse<GenresMovieQuery.Data> dataApolloResponse) {
                    if (dataApolloResponse.hasErrors()) {
                        Log.e("ApolloError", "GraphQL Errors: " + dataApolloResponse.errors);
                    } else {
                        GenresMovieQuery.Data data = dataApolloResponse.data;
                        if (data != null) {
                            AppDelegate.getInstance().setGenreList(data.getGenres());
                            genresAdapter = new GenresAdapter(mContext, data.getGenres());
                            binding.genresRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                            binding.genresRecycler.setAdapter(genresAdapter);
                        } else {
                            Log.e("ApolloError", "Received null data");
                        }
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.e("ApolloError", "Error fetching data", e);

                }
            });

            Rx3Apollo.single(apolloClient.query(trendingMoviesQuery)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApolloResponse<TrendingMoviesQuery.Data>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                }

                @Override
                public void onSuccess(@NonNull ApolloResponse<TrendingMoviesQuery.Data> dataApolloResponse) {
                    if (dataApolloResponse.hasErrors()) {
                        Log.e("ApolloError", "GraphQL Errors: " + dataApolloResponse.errors);
                    } else {
                        TrendingMoviesQuery.Data list = dataApolloResponse.data;
                        if (list != null) {
                            Log.d("TrendingMovies", new Gson().toJson(list));
                            listAdapter = new ListAdapter(mContext, list.getTrendingMovies());
                            binding.recyclerTrending.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                            binding.recyclerTrending.setAdapter(listAdapter);
                        } else {
                            Log.e("ApolloError", "Received null data");
                        }
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.e("ApolloError", "Error fetching data", e);
                }
            });

            Rx3Apollo.single(apolloClient.query(popularMoviesQuery)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApolloResponse<PopularMoviesQuery.Data>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onSuccess(@NonNull ApolloResponse<PopularMoviesQuery.Data> dataApolloResponse) {
                    if (dataApolloResponse.hasErrors()) {
                        Log.e("ApolloError", "GraphQL Errors: " + dataApolloResponse.errors);
                    } else {
                        PopularMoviesQuery.Data list = dataApolloResponse.data;
                        if (list != null) {
                            Log.d("PopularMovies", new Gson().toJson(list));
                            listViewAdapter = new ListViewAdapter(mContext, list.getPopularMovies());
                            binding.recyclerPopular.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                            binding.recyclerPopular.setAdapter(listViewAdapter);
                        } else {
                            Log.e("ApolloError", "Received null data");
                        }
                    }

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Log.e("ApolloError", "Error fetching data", e);
                }
            });


        }

        return binding.getRoot();
    }
}