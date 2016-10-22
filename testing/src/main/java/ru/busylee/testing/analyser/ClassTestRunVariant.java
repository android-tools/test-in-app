package ru.busylee.testing.analyser;

import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by busylee on 21.10.16.
 */

public class ClassTestRunVariant implements TestRun {

  private final String name;
  private final String description;

  List<Class> testClasses = new ArrayList<>();

  ClassTestRunVariant(String name, String description) {
    this.name = name;
    this.description = description;
  }

  ClassTestRunVariant(String name, String description, Class testClass) {
    this(name, description);
    this.testClasses.add(testClass);
  }

  ClassTestRunVariant(String name, String description, List<Class> testClasses) {
    this(name, description);
    this.testClasses.addAll(testClasses);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void applyToBundle(Bundle bundle) {
    List<String> classNames = new ArrayList<>(testClasses.size());
    for(Class clazz: testClasses) {
      classNames.add(clazz.getName());
    }

    if(classNames.size() > 0) {
      bundle.putString("class", TextUtils.join(",", classNames));
    }
  }
}
