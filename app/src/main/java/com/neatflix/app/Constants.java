package com.neatflix.app;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static boolean isNetworkDialogOpened = false;
    public static String API_DEFAULT_URL = "https://api.themoviedb.org/3/";
    public static String API_DOC_UPLOAD_URL = "api_doc_upload_url";
    public static String BASE_BACKDROP_IMAGE_URL = "https://image.tmdb.org/t/p/w780";
    public static String BASE_POSTER_IMAGE_URL =  "https://image.tmdb.org/t/p/w500";


    public enum collectionType {// Collection types = Promis to pay - PTP, Full Pay - FP, Partial Pay - PP
        PTP, FP, PP
    }

    public enum paymentType {// Payment types = Cash - C, Cheque - CH, NEFT - N, UPI - U, Not Available - NA
        C, CH, U, NA, N
    }

    public enum searchType { // Name - N, Address - A, Code - C, Phone -P
        N, A, C, P
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static ListAdapter getAdapter(Context mContext, String[] list) {
        return new ArrayAdapter(mContext, android.R.layout.simple_spinner_dropdown_item, list);
    }

    public static <T> List<T> changeArraytoList(T[] array) {
        List<T> tList = new ArrayList<>();
        Collections.addAll(tList, array);
        return tList;
    }

}
