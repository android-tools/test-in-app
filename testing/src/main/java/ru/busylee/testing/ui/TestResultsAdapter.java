package ru.busylee.testing.ui;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.busylee.testing.R;
import ru.busylee.testing.model.TestDescription;

/**
 * Created by busylee on 16.10.16.
 */

public class TestResultsAdapter extends BaseAdapter {

  private final LayoutInflater mLayoutInflater;
  private final List<TestDescription> testDescriptions = new ArrayList<>();

  public TestResultsAdapter(Context context) {
    mLayoutInflater = LayoutInflater.from(context);
  }

  public void addAll(List<TestDescription> testDescriptions) {
    this.testDescriptions.addAll(testDescriptions);
  }

  @Override
  public int getCount() {
    return testDescriptions.size();
  }

  @Override
  public TestDescription getItem(int i) {
    return testDescriptions.get(i);
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    Holder holder;
    if(view == null) {
      view = mLayoutInflater.inflate(R.layout.i_test_layout, viewGroup, false);
      holder = new Holder(view);
      view.setTag(holder);
    } else {
      holder = (Holder) view.getTag();
    }

    TestDescription item = getItem(i);
    holder.tvName.setText(item.name);
    holder.tvResult.setText(item.result);

    //TODO remove this shit
    if("success".equals(item.result)) {
      holder.tvResult.setTextColor(Color.GREEN);
    } else {
      holder.tvResult.setTextColor(Color.RED);
    }

    return view;
  }

  class Holder {
    final TextView tvName;
    final TextView tvResult;

    public Holder(View view) {
      tvName = (TextView) view.findViewById(R.id.tv_test_name);
      tvResult = (TextView) view.findViewById(R.id.tv_test_result);
    }

  }

}
