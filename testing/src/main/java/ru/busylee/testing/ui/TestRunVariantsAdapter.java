package ru.busylee.testing.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.busylee.testing.R;
import ru.busylee.testing.analyser.TestAnalyzer;
import ru.busylee.testing.analyser.TestRun;

/**
 * Created by busylee on 21.10.16.
 */

public class TestRunVariantsAdapter extends BaseAdapter {
  private final LayoutInflater mLayoutInflater;
  private final List<TestRun> testRuns = new ArrayList<>();

  public TestRunVariantsAdapter(Context context) {
    mLayoutInflater = LayoutInflater.from(context);
  }

  public void addAll(List<TestRun> testDescriptions) {
    this.testRuns.addAll(testDescriptions);
  }

  @Override
  public int getCount() {
    return testRuns.size();
  }

  @Override
  public TestRun getItem(int i) {
    return testRuns.get(i);
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    TestRunVariantsAdapter.Holder holder;
    if(view == null) {
      view = mLayoutInflater.inflate(R.layout.i_test_run_variant, viewGroup, false);
      holder = new TestRunVariantsAdapter.Holder(view);
      view.setTag(holder);
    } else {
      holder = (TestRunVariantsAdapter.Holder) view.getTag();
    }

    TestRun item = getItem(i);
    holder.tvName.setText(item.getName());
    holder.tvDescription.setText(item.getDescription());

    return view;
  }

  private class Holder {
    final TextView tvName;
    final TextView tvDescription;

    public Holder(View view) {
      tvName = (TextView) view.findViewById(R.id.tv_test_name);
      tvDescription = (TextView) view.findViewById(R.id.tv_run_description);
    }

  }
}
