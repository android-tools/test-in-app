package ru.busylee.testing;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import ru.busylee.testing.utils.ClassLoaderInjector;

/**
 * Created by busylee on 17.10.16.
 */

public class MyAppDelegate {

  private static final boolean DEBUG_MODE = false;
  private static final String TAG = "MyAppDelegate";

  public static void startInstrumentation(Context context) {
    Bundle arguments = new Bundle();
    if(DEBUG_MODE) {
      arguments.putString("debug", "true");
    }
    arguments.putString("package", "ru.busylee.runtestinapp");
    arguments.putString("name", "ru.busylee.testing.MyCustomRunner");
    arguments.putString("listener", "ru.busylee.testing.MyRunListener");
    if (!context.startInstrumentation(new ComponentName(context, MyCustomRunner.class), null, arguments)) {
      Log.d(TAG, "startInstrumentation failure");
    }
  }

  public void attachBaseContext(Context base) {
    try {
      final File optimizedDexOutputPath = base.getCacheDir();
      ClassLoaderInjector.add(new File(Environment.getExternalStorageDirectory() + "/test.apk"), optimizedDexOutputPath, (PathClassLoader) base.getClassLoader());
    }
    catch (Exception e) {
      Log.e(TAG, "Did not find secondary dex. Going ahead with normal startup", e);
    }
  }

  /**
   * File I/O code to copy the secondary dex file from asset resource to
   * internal storage.
   */
  private void prepareDex(File dexInternalStoragePath) throws IOException
  {
    BufferedInputStream bis = null;
    OutputStream dexWriter = null;

    // Buffer size for file copying. While 8kb is used in this sample, you
    // may want to tweak it based on actual size of the secondary dex file
    // involved.
    final int BUF_SIZE = 8 * 1024;

    try {
      bis = new BufferedInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/test.apk")));
      dexWriter = new BufferedOutputStream(new FileOutputStream(dexInternalStoragePath));
      byte[] buf = new byte[BUF_SIZE];
      int len;
      while ((len = bis.read(buf, 0, BUF_SIZE)) > 0) {
        dexWriter.write(buf, 0, len);
      }
    }
    finally {
      if (dexWriter != null) {
        dexWriter.close();
      }

      if (bis != null) {
        bis.close();
      }
    }
  }

  private void install(Context context) {
    ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
    // must set the context classloader for apps that use a shared uid, see
    // frameworks/base/core/java/android/app/LoadedApk.java
    ClassLoader newClassLoader = convertClassLoader(context, originalClassLoader);
    Log.i(TAG, String.format("Setting context classloader to '%s', Original: '%s'",
            newClassLoader.toString(), originalClassLoader.toString()));
    Thread.currentThread().setContextClassLoader(newClassLoader);
  }


  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private ClassLoader convertClassLoader(Context context, ClassLoader classLoader) {
    File dexOutputDir = context.getCodeCacheDir();
    DexClassLoader dexClassLoader = new DexClassLoader(
            Environment.getExternalStorageDirectory().getPath() + "/test.apk",
            dexOutputDir.getAbsolutePath(),
            null,
            classLoader);
    return dexClassLoader;
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
