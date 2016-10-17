package ru.busylee.testing.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import ru.busylee.testing.R;
import ru.busylee.testing.storage.TestStorage;
import ru.busylee.testing.storage.TestStorageImpl;

/**
 * Created by busylee on 16.10.16.
 */

public class TestResultActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.a_test_activity);

    ListView testResultList = (ListView) findViewById(R.id.lv_test_result_list);
    TestResultsAdapter testResultsAdapter = new TestResultsAdapter(this);
    testResultList.setAdapter(testResultsAdapter);

    TestStorage testStorage = TestStorageImpl.getInstance(this);
    //TODO I know it is ugly to load on MainThread. I do not care now...
    testResultsAdapter.addAll(testStorage.getLastTestResults());
    testResultsAdapter.notifyDataSetChanged();
  }
}
