package ru.busylee.testing.analyser;

import android.os.Bundle;

/**
 * Created by busylee on 21.10.16.
 */
public interface TestRun {
  String getName();
  String getDescription();
  void applyToBundle(Bundle bundle);
}
