package ru.busylee.testing.storage;

import java.util.List;

import ru.busylee.testing.model.TestDescription;

/**
 * Created by busylee on 16.10.16.
 */
public interface TestStorage {
  void clear();
  void store(TestDescription testDescription);
  List<TestDescription> getLastTestResults();
}
