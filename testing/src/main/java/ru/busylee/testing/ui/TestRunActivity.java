package ru.busylee.testing.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ru.busylee.testing.MyInstrumentationHelper;
import ru.busylee.testing.R;
import ru.busylee.testing.analyser.TestAnalyzer;
import ru.busylee.testing.analyser.TestRun;

/**
 * Created by busylee on 21.10.16.
 */

public class TestRunActivity extends Activity implements AdapterView.OnItemClickListener {
  private TestRunVariantsAdapter mTestRunVariantsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.a_test_run_activity);
    ListView runVariants = ((ListView) findViewById(R.id.lv_test_run_variants));
    mTestRunVariantsAdapter = new TestRunVariantsAdapter(this);
    runVariants.setAdapter(mTestRunVariantsAdapter);
    runVariants.setOnItemClickListener(this);

    //TODO move to background
    findTestVariants();
  }

  private void findTestVariants() {
    try {
      List<TestRun> testVariants = new TestAnalyzer().findTestRunsInTestApk(this,
              Environment.getExternalStorageDirectory() + "/test.apk");
      mTestRunVariantsAdapter.addAll(testVariants);
      mTestRunVariantsAdapter.notifyDataSetChanged();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
      Toast.makeText(this, "Something goes wrong", Toast.LENGTH_SHORT).show();
    }

  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    TestRun item = mTestRunVariantsAdapter.getItem(position);
    if(item != null) {
      MyInstrumentationHelper.startInstrumentation(this, item);
    }
  }

}
