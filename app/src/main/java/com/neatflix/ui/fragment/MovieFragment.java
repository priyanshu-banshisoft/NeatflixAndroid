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
import com.neatflix.R;
import com.neatflix.TrendingMoviesQuery;
import com.neatflix.app.BaseApplication;
import com.neatflix.app.BaseFragment;
import com.neatflix.databinding.FragmentMovieBinding;
import com.neatflix.ui.adapter.ListAdapter;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieFragment extends BaseFragment {

    FragmentMovieBinding binding;
    ApolloClient apolloClient;
    ListAdapter listAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        BaseApplication application = (BaseApplication) requireActivity().getApplication();
        apolloClient = application.getApolloClient();
        TrendingMoviesQuery trendingMoviesQuery = new TrendingMoviesQuery();
        showLoader();
        Rx3Apollo.single(apolloClient.query(trendingMoviesQuery))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApolloResponse<TrendingMoviesQuery.Data>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull ApolloResponse<TrendingMoviesQuery.Data> dataApolloResponse) {
                        hideLoader();
                        if (dataApolloResponse.hasErrors()) {
                            Log.e("ApolloError", "GraphQL Errors: " + dataApolloResponse.errors);
                        } else {
                            TrendingMoviesQuery.Data list = dataApolloResponse.data;
                            if (list != null) {
                                Log.d("TrendingMovies", new Gson().toJson(list));
                                listAdapter = new ListAdapter(mContext, list.getTrendingMovies());
                                binding.recyclerPopular.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                                binding.recyclerPopular.setAdapter(listAdapter);
                            } else {
                                Log.e("ApolloError", "Received null data");
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        hideLoader();
                        Log.e("ApolloError", "Error fetching data", e);
                    }
                });

        return binding.getRoot();
    }
}