package ru.busylee.testing.analyser;

import android.content.Context;
import android.util.Log;

import org.junit.Test;
import org.junit.runners.Suite;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;
import ru.busylee.testing.MyInstrumentationHelper;

import static android.content.ContentValues.TAG;

/**
 * Created by busylee on 21.10.16.
 */

public class TestAnalyzer {

  //TODO split
  public List<TestRun> findTestRunsInTestApk(Context context, String pathToTestApk) throws ClassNotFoundException, IOException {
    List<TestRun> testRuns = new ArrayList<>();

    DexFile dexFile = DexFile.loadDex(pathToTestApk, File.createTempFile("opt", "dex",
            context.getCacheDir()).getPath(), 0);
    ClassLoader classLoader = new MyInstrumentationHelper()
            .getTestClassLoader(context, Thread.currentThread().getContextClassLoader());

    // find classes with
    List<Class> classes = new ArrayList<>();
    List<Class> suiteClasses = new ArrayList<>();

    Enumeration<String> entries = dexFile.entries();

    while (entries.hasMoreElements()) {
      String entry = entries.nextElement();
        Log.d(TAG, "Entry: " + entry);
        if(entry.startsWith("ru.busylee.runtestinapp")) {
          Class<?> entryClass = classLoader.loadClass(entry);//dexFile.loadClass(entry, classLoader);
          if (entryClass != null) {
            Annotation[] annotations = entryClass.getAnnotations();
            for (Annotation annotation : annotations) {
              if (annotation instanceof Test) {
                if (!classes.contains(entryClass)) {
                  classes.add(entryClass);
                }
              }
              if (annotation instanceof Suite.SuiteClasses) {
                if (!suiteClasses.contains(entryClass)) {
                  suiteClasses.add(entryClass);
                }
              }
            }
          }
        }
    }

    testRuns.add(new ClassTestRunVariant(
            "All in package",
            "Will run all test classes in package",
            classes
    ));

    for (Class clazz : suiteClasses) {
      testRuns.add(new ClassTestRunVariant(
              "Test suite " + "(" + clazz.getName() + ")",
              "Will run all test in specified test suite",
              clazz
      ));
    }

    return testRuns;
  }


}
