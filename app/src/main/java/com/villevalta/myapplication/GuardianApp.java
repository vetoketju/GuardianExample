package com.villevalta.myapplication;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.villevalta.myapplication.network.GuardianApiService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by villevalta on 8/14/17.
 */

public class GuardianApp extends MultiDexApplication {

    String TAG = "GuardianApp";

    private static GuardianApp instance;
    private GuardianApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        apiService = new GuardianApiService();


        // Picasson logging enabled
        Picasso picasso = new Picasso.Builder(this).loggingEnabled(true).indicatorsEnabled(true).build();
        Picasso.setSingletonInstance(picasso);

        initRealm();
    }

    private void initRealm(){
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }


    public static GuardianApp getInstance(){
        return instance;
    }

    public GuardianApiService getApiService() {
        return apiService;
    }
}
