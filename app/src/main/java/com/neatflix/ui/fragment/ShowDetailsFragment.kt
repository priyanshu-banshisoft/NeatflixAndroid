package com.neatflix.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.rx3.Rx3Apollo
import com.bumptech.glide.Glide
import com.neatflix.CreditQuery
import com.neatflix.GenresMovieQuery
import com.neatflix.MovieQuery
import com.neatflix.R
import com.neatflix.app.BaseApplication
import com.neatflix.app.BaseFragment
import com.neatflix.app.Constants
import com.neatflix.databinding.FragmentShowDetailsBinding
import com.neatflix.ui.adapter.CastAdapter
import com.neatflix.ui.adapter.GenresAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*


class ShowDetailsFragment : BaseFragment() {

    lateinit var binding: FragmentShowDetailsBinding
    private var apolloClient: ApolloClient? = null
    var id: Int? = null
    var genresList = ArrayList<GenresMovieQuery.Genre>()

    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_details, container, false)
        if (arguments != null) {
            id = requireArguments().getInt("id")
        }
        val application = requireActivity().application as BaseApplication
        apolloClient = application.apolloClient
        val movieQuery = MovieQuery(id!!)
        val creditQuery = CreditQuery(id!!)

        binding.backIcn.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }


        Rx3Apollo.single(apolloClient!!.query(movieQuery)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribe(object : SingleObserver<ApolloResponse<MovieQuery.Data>> {
                override fun onSubscribe(d: Disposable) {
                }

                @RequiresApi(Build.VERSION_CODES.N)
                @SuppressLint("CheckResult")
                override fun onSuccess(t: ApolloResponse<MovieQuery.Data>) {
                    if (t.hasErrors()) {
                        Log.e("ApolloError", "GraphQL Errors: " + t.errors)
                    } else {
                        val movieData: MovieQuery.Data? = t.data

                        Glide.with(mContext)
                            .load(Constants.BASE_BACKDROP_IMAGE_URL + movieData!!.movie!!.backdrop_path)
                            .into(binding.backImg)
                        Glide.with(mContext)
                            .load(Constants.BASE_POSTER_IMAGE_URL + movieData.movie!!.poster_path)
                            .into(binding.imageIv)
                        binding.adultTv.text = if (movieData.movie.adult == true) "18+" else "PG"
                        binding.nameTv.text = movieData.movie.title
                        binding.releaseTv.text = movieData.movie.release_date
                        binding.ratingBar.rating = (movieData.movie.vote_average?.div(2))!!.toFloat()
                        movieData.movie.genres!!.forEach {
                            genresList.add(GenresMovieQuery.Genre(it!!.id,it.name))
                        }
                        binding.genresRecycler.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
                        binding.genresRecycler.adapter = GenresAdapter(mContext,genresList)
                        binding.overviewTxt.text = movieData.movie.overview
                    }
                }
                override fun onError(e: Throwable) {
                    Log.e("ApolloError", "Error fetching data", e)
                }
            })

        Rx3Apollo.single(apolloClient!!.query(creditQuery))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribe(object : SingleObserver<ApolloResponse<CreditQuery.Data>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(t: ApolloResponse<CreditQuery.Data>) {
                    if (t.hasErrors()) {
                        Log.e("ApolloError", "GraphQL Errors: " + t.errors)
                    } else {
                        val creditData = t.data
                        binding.castRecycler.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
                        binding.castRecycler.adapter = CastAdapter(mContext, creditData!!.credit!!.cast)
                    }
                }

                override fun onError(e: Throwable) {
                    Log.e("ApolloError", "Error fetching data", e)
                }

            })



        return binding.root
    }
}