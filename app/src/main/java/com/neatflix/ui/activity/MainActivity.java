package com.neatflix.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.rx3.Rx3Apollo;
import com.neatflix.PopularMoviesQuery;
import com.neatflix.R;
import com.neatflix.app.BaseActivity;
import com.neatflix.app.BaseApplication;
import com.neatflix.databinding.ActivityMainBinding;
import com.neatflix.utils.StatusBarUtils;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainActivity extends BaseActivity {
    private ApolloClient apolloClient;
    ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        StatusBarUtils.statusBarColor(this,R.color.dark_purple);
        navController =  Navigation.findNavController(this,R.id.navHome);

//        BaseApplication application = (BaseApplication) getApplication();
//        apolloClient = application.getApolloClient();
//        PopularMoviesQuery popularMoviesQuery = new PopularMoviesQuery();
//
//
//        Rx3Apollo.single(apolloClient.query(popularMoviesQuery))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new SingleObserver<ApolloResponse<PopularMoviesQuery.Data>>() {
//                            @Override
//                            public void onSubscribe(@NonNull Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(@NonNull ApolloResponse<PopularMoviesQuery.Data> dataApolloResponse) {
//                               if (dataApolloResponse.hasErrors()){
//
//                               } else {
//                                   PopularMoviesQuery.Data list = dataApolloResponse.data;
//                                   assert list != null;
//                                   Log.d("1st Element", Objects.requireNonNull(list.getPopularMovies()).get(0).getTitle());
//                               }
//                            }
//
//                            @Override
//                            public void onError(@NonNull Throwable e) {
//
//                            }
//                        });


    }
}