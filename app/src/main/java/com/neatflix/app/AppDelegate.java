package com.neatflix.app;

import com.neatflix.GenresMovieQuery;

import java.util.List;

public class AppDelegate {
    public static AppDelegate ad;
    public List<GenresMovieQuery.Genre> genreList;

    public static AppDelegate getInstance() {
        if (ad == null) {
            ad = new AppDelegate();
        }
        return ad;
    }

    public static void setInstance(AppDelegate instance) {
        AppDelegate.ad = instance;
    }


    public static AppDelegate getAd() {
        return ad;
    }

    public static void setAd(AppDelegate ad) {
        AppDelegate.ad = ad;
    }

    public  List<GenresMovieQuery.Genre> getGenreList() {
        return genreList;
    }

    public  void setGenreList(List<GenresMovieQuery.Genre> genreList) {
        this.genreList = genreList;
    }
}
