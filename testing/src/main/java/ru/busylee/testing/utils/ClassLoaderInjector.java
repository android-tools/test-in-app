package ru.busylee.testing.utils;

import android.content.Context;

/**
 * Created by busylee on 05.12.16.
 */

public interface ClassLoaderInjector {
  void inject(String pathToApk, Context context);
}
