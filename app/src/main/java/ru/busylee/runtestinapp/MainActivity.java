package ru.busylee.runtestinapp;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.busylee.testing.MyCustomRunner;
import ru.busylee.testing.ui.TestResultActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start_test).setOnClickListener(this);
        findViewById(R.id.show_results).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_test:
                Bundle arguments = new Bundle();
                arguments.putString("name", "ru.busylee.testing.MyCustomRunner");
                arguments.putString("listener", "ru.busylee.testing.MyRunListener");
                if (!startInstrumentation(new ComponentName(this, MyCustomRunner.class), null, arguments)) {
                    Log.d(TAG, "startInstrumentation failure");
                }
                break;
            case R.id.show_results: {
                Intent intent = new Intent(this, TestResultActivity.class);
                startActivity(intent);
                break;
            }
        }

    }
}
