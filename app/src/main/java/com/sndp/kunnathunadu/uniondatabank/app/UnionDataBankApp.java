package com.sndp.kunnathunadu.uniondatabank.app;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by akhil on 4/3/17.
 */

public class UnionDataBankApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
