package ru.busylee.runtestinapp;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.facebook.stetho.Stetho;

import ru.busylee.testing.MyAppDelegate;

/**
 * Created by busylee on 15.10.16.
 */

public class MyApplication extends Application {

  private static final String TAG = "MyApplication";

  private MyAppDelegate appDelegate = new MyAppDelegate();

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    Log.d(TAG, "attachBaseContext()");
    appDelegate.attachBaseContext(base);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
