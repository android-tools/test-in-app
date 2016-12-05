package ru.busylee.testing.utils;

/**
 * Created by busylee on 18.10.16.
 */
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;

public class ReflectionInjector implements ClassLoaderInjector {

  private static final String TAG = "ClassLoaderInjectorOld";

  private static Object getField(Object paramObject, Class<?> paramClass, String paramString)
          throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException
  {
    Field localField = paramClass.getDeclaredField(paramString);
    localField.setAccessible(true);
    return localField.get(paramObject);
  }
  private static void setField(Object paramObject1, Class<?> paramClass, String paramString, Object paramObject2)
          throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException
  {
    Field localField = paramClass.getDeclaredField(paramString);
    localField.setAccessible(true);
    localField.set(paramObject1, paramObject2);
  }
  private static Object getPathList(BaseDexClassLoader paramBaseDexClassLoader) throws NoSuchFieldException,
          IllegalAccessException, IllegalArgumentException
  {
    return getField(paramBaseDexClassLoader, BaseDexClassLoader.class, "pathList");
  }
  private static Object getDexElements(Object paramObject) throws NoSuchFieldException, IllegalAccessException,
          IllegalArgumentException
  {
    return getField(paramObject, paramObject.getClass(), "dexElements");
  }
  private static void setDexElements(Object paramObject1, Object paramObject2) throws NoSuchFieldException,
          IllegalAccessException, IllegalArgumentException
  {
    setField(paramObject1, paramObject1.getClass(), "dexElements", paramObject2);
  }
  @SuppressWarnings ("rawtypes")
  private static Object joinArrays(Object paramObject1, Object paramObject2)
  {
    Class localClass = paramObject1.getClass().getComponentType();
    int i = Array.getLength(paramObject1);
    int j = i + Array.getLength(paramObject2);
    Object localObject = Array.newInstance(localClass, j);
    int k = 0;
    while (k < i) {
      Array.set(localObject, k, Array.get(paramObject1, k));
      k++;
    }
    while (k < j) {
      Array.set(localObject, k, Array.get(paramObject2, k - i));
      k++;
    }
    return localObject;
  }
  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  public static void add(File dexFile, File optimizedDir, PathClassLoader paramPathClassLoader)
  {
    try {
      Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
      Constructor<?> constructor =
              dexPathListClass.getConstructor(ClassLoader.class, String.class, String.class,
                      File.class);
      constructor.setAccessible(true);
      Object dexPathList =
              constructor.newInstance(paramPathClassLoader, dexFile.getAbsolutePath(), null, optimizedDir);
      Object localObject = joinArrays(getDexElements(getPathList(paramPathClassLoader)),
              getDexElements(dexPathList));
      setDexElements(getPathList(paramPathClassLoader), localObject);
    }
    catch (Exception e) {
      Log.e(TAG, "Error", e);
    }
  }

  @Override
  public void inject(String pathToApk, Context context) {
    try {
      final File optimizedDexOutputPath = context.getCacheDir();
      ReflectionInjector.add(new File(pathToApk), optimizedDexOutputPath, (PathClassLoader) context.getClassLoader());
    }
    catch (Exception e) {
      Log.e(TAG, "Did not find secondary dex. Going ahead with normal startup", e);
    }
  }
}
