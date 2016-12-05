package ru.busylee.testing;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import ru.busylee.testing.analyser.TestRun;
import ru.busylee.testing.utils.ClassLoaderInjector;
import ru.busylee.testing.utils.ReflectionInjector;

/**
 * Created by busylee on 17.10.16.
 */

public class MyInstrumentationHelper {

//  private static final boolean DEBUG_MODE = true;
  private static final boolean DEBUG_MODE = false;

  private static final String TAG = "MyInstrumentationHelper";

  public static void startInstrumentation(Context context, TestRun testRun) {
    Bundle arguments = new Bundle();
    if(DEBUG_MODE) {
      arguments.putString("debug", "true");
    }
    testRun.applyToBundle(arguments);
    arguments.putString("listener", "ru.busylee.testing.MyRunListener");
    if (!context.startInstrumentation(new ComponentName(context, MyCustomRunner.class), null, arguments)) {
      Log.d(TAG, "startInstrumentation failure");
    }
  }

  public static void inject(String pathToTestApk, Context base) {
    ClassLoaderInjector classLoaderInjector = new ReflectionInjector();
    classLoaderInjector.inject(pathToTestApk, base);
  }

  public void check() {
    checkClass(Thread.currentThread().getContextClassLoader());
  }

  public void checkClass(ClassLoader contextClassLoader) {
    try {
      contextClassLoader.loadClass("ru.busylee.runtestinapp.MainActivityCaseR");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

}
