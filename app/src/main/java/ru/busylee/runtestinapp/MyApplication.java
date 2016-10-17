package ru.busylee.runtestinapp;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;

/**
 * Created by busylee on 15.10.16.
 */

public class MyApplication extends Application {

  private static final String TAG = "MyApplication";

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d(TAG, "onCreate()");
    Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build()
    );
  }



}
