package ru.busylee.testing;

import android.os.Bundle;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

/**
 * Created by busylee on 15.10.16.
 */
public class MyCustomRunner extends AndroidJUnitRunner {

  private static final String TAG = "MyCustomRunner";

  @Override
  public void onCreate(Bundle arguments) {
    super.onCreate(arguments);
    Log.d(TAG, "onCreate()");
    //read from arguments
    String pathToTestApk = Environment.getExternalStorageDirectory() + "/test.apk";
    MyInstrumentationHelper.inject(pathToTestApk, InstrumentationRegistry.getContext());
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart()");
  }

  @Override
  public void finish(int resultCode, Bundle results) {
    super.finish(resultCode, results);
    Log.d(TAG, "finish()");
  }

  @Override
  public boolean onException(Object obj, Throwable e) {
    Log.d(TAG, "onException()");
    return super.onException(obj, e);
  }



}
