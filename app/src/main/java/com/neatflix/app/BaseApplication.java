package com.neatflix.app;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;


import com.apollographql.apollo3.ApolloClient;
import com.apollographql.apollo3.api.ApolloRequest;
import com.apollographql.apollo3.api.ApolloResponse;
import com.apollographql.apollo3.api.Operation;
import com.apollographql.apollo3.interceptor.ApolloInterceptor;
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain;
import com.google.firebase.FirebaseApp;
import com.neatflix.BuildConfig;
import com.neatflix.helper.CheckInternetConnection;
import com.neatflix.helper.PreferenceManger;

import java.io.File;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import kotlinx.coroutines.flow.Flow;

public class BaseApplication extends MultiDexApplication {

    private static final String TAG = "BaseApplication";
    private static BaseApplication instance;
    private static PreferenceManger preferenceManger;

    private ApolloClient apolloClient;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseApp.initializeApp(this);
        File file = getCodeCacheDir();
        file.setReadOnly();
        apolloClient = new ApolloClient.Builder()
                .serverUrl("https://tmdbgraphql-21xuo56l.b4a.run/graphql")
                .addHttpHeader("API_KEY",BuildConfig.API_KEY)
                .build();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
                                .build())).build());

    }


    public static BaseApplication getInstance() {
        if (instance == null)
            instance = new BaseApplication();
        return instance;
    }

    public ApolloClient getApolloClient() {
        return apolloClient;
    }

    public static PreferenceManger getPreferenceManger() {
        if (preferenceManger == null && getInstance() != null) {

            preferenceManger = new PreferenceManger(getInstance().getSharedPreferences(PreferenceManger.PREF_KEY, Context.MODE_PRIVATE));
        }
        return preferenceManger;
    }


    public boolean isInternetConnected(Context context) {
        if (new CheckInternetConnection(this).isConnected())
            return true;
        else {
            if (!Constants.isNetworkDialogOpened) {
                Constants.isNetworkDialogOpened = true;
                ((BaseActivity) context).hideLoader();
                ((BaseActivity) context).showAlertDialog("Network Error!!", "Please enable data or wifi connection", "OK", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Constants.isNetworkDialogOpened = false;
                    }
                });
            }
        }
        return false;
    }

}
